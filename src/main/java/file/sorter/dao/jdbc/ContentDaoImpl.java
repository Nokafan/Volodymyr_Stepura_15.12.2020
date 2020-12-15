package file.sorter.dao.jdbc;

import file.sorter.dao.ContentDao;
import file.sorter.exception.DataProcessingException;
import file.sorter.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ContentDaoImpl implements ContentDao {

    public static final String PARENT_ID = "ParentID";
    public static final String FILE_NAME = "FileName";
    public static final String CONTENT_ID = "ContentID";

    @Override
    public List<Long> getParentIdsByContentIds(List<Long> contentIds) {
        String ids = contentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        String query = "SELECT DISTINCT c.ParentID FROM san_content_999_calculated c "
                + "WHERE c.ContentID in (" + ids + ")";
        List<Long> listIds = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listIds.add(resultSet.getLong(PARENT_ID));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get ParentID for Set<Long> contentIds", e);
        }
        return listIds;
    }

    @Override
    public List<Long> getContentIdsByFileNameContains(String contains) {
        String query = "SELECT c.ContentID FROM  san_content_999_calculated c "
                + "WHERE c.Status <> 'DELETED' AND c.IsDirectory = FALSE AND c.FileName LIKE ?";
        List<Long> listIds = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, contains);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listIds.add(resultSet.getLong(CONTENT_ID));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get parentId with extension " + contains, e);
        }
        return listIds;
    }

    @Override
    public List<Long> getContentIdsToCopy() {
        String query = "SELECT c.ContentID FROM san_content_999_calculated c "
                + "WHERE c.Status <> 'DELETED' AND c.IsDirectory = FALSE "
                + "AND c.FileName NOT RLIKE '.tif' "
                + "AND c.FileName NOT RLIKE '.mov' "
                + "AND c.FileName NOT RLIKE '.avi'";
        List<Long> listIds = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listIds.add(resultSet.getLong(CONTENT_ID));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get ContentId for files except *.tif ", e);
        }
        return listIds;
    }

    @Override
    public List<String> getFileNamesByIds(List<Long> contentIds) {
        String ids = contentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        String query = "SELECT DISTINCT c.FileName FROM san_content_999_calculated c "
                + "WHERE c.ContentID in (" + ids + ")";
        List<String> listFiles = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listFiles.add(resultSet.getString(FILE_NAME));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get FileName for Set<Long> contentIds", e);
        }
        return listFiles;
    }
}
