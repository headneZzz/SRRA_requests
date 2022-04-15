package ru.gosarhro.SRRA_requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.gosarhro.SRRA_requests.form.UnloadForm;
import ru.gosarhro.SRRA_requests.util.UnloadFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnloadService {

    private final RubricService rubricService;
    private final SourceService sourceService;
    private final ExecutorService executorService;
    private final ComputerWithAccessService computerWithAccessService;
    private final JdbcTemplate jdbcTemplate;
    private List<UnloadForm> unloadModels;
    private int countOfRequests;

    public String unloadFromDb(Model model, HttpServletRequest servletRequest) {
        model.addAttribute("unloadFilter", new UnloadFilter());
        model.addAttribute("rubrics", rubricService.getAllOrderById());
        model.addAttribute("sources", sourceService.getAll());
        model.addAttribute("executors", executorService.getAll());
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr())) {
            model.addAttribute("isPersonal", true);
        }
        return "unload";
    }

    public String unloadFromDbWithFilter(UnloadFilter unloadFilter, Model model, HttpServletRequest servletRequest) {
        if (unloadFilter.getRubrics() != null) {
            countOfRequests = 0;
            unloadModels = new ArrayList<>();
            connectToDbAndCountUp(unloadFilter);

            model.addAttribute("countOfRecords", countOfRequests);
            model.addAttribute("unloadModels", unloadModels);

        } else {
            model.addAttribute("errorMessage", "Не заданы рубрики");
        }
        model.addAttribute("rubrics", rubricService.getAllOrderById());
        model.addAttribute("sources", sourceService.getAll());
        model.addAttribute("executors", executorService.getAll());
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr())) {
            model.addAttribute("isPersonal", true);
        }
        return "unload";
    }

    public void connectToDbAndCountUp(UnloadFilter unloadFilter) {
        String query = generateSqlQuery(
                unloadFilter.getRubrics(),
                unloadFilter.getSourceIds(),
                unloadFilter.getExecutorId(),
                unloadFilter.getIsEntity(),
                unloadFilter.getDateFrom(),
                unloadFilter.getDateTo()
        );
        unloadModels = jdbcTemplate.query(query, new BeanPropertyRowMapper(UnloadForm.class));
        unloadModels.forEach(unloadForm -> countOfRequests += unloadForm.getCountOfRequests());
    }

    private String generateSqlQuery(
            String[] rubricCodes,
            int[] sourceIds,
            int executorId,
            String isEntity,
            String dateFrom,
            String dateTo
    ) {
        StringBuilder sqlQuery = new StringBuilder("SELECT rubric_code, SUM(copy_number) as copy_number, COUNT(requests.rubric_id) as count_of_requests" +
                "FROM requests.requests " +
                "INNER JOIN requests.rubrics ON requests.rubric_id = rubrics.rubric_id " +
                "WHERE requests.rubric_id IN (" +
                "SELECT rubric_id " +
                "FROM requests.rubrics WHERE ");
        for (String rubric : rubricCodes) {
            sqlQuery.append("rubric_code = ").append('\'').append(rubric).append('\'').append(" OR ");
        }
        sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length()).append(')');

        if (sourceIds != null) {
            sqlQuery.append(" AND (");
            for (int sourceId : sourceIds)
                sqlQuery.append("source_id = ").append(sourceId).append(" OR ");
            sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length()).append(')');
        }
        if (executorId != 0) {
            sqlQuery.append(" AND executor_id = ").append(executorId);
        }
        if (!isEntity.equals("0")) {
            sqlQuery.append(" AND is_entity = ").append(isEntity);
        }
        if (!dateFrom.equals("")) {
            sqlQuery.append(" AND end_date >= '").append(dateFrom).append('\'');
        }
        if (!dateTo.equals("")) {
            sqlQuery.append(" AND end_date <= '").append(dateTo).append('\'');
        }
        sqlQuery.append(" GROUP BY rubric_code");

        return sqlQuery.toString();
    }
}
