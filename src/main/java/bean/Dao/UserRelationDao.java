package bean.Dao;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-21
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public interface UserRelationDao {
	public String watchSomeOne(String watcher,String beWatcher);
	public String unWatchSomeOne(String watcher,String beWatcher);
}