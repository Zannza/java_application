
public class Task {
	private String name,supplement,date;
	public Task(String n,String date_data, String s){
		date = date_data;name = n;supplement = s;
	}
	public String Ret_date() {
		return date;
	}
	public String Ret_name() {
		return name;
	}
	public String Ret_supplement() {
		return supplement;
	}
}