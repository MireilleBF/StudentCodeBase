package revisionDS_POO1_23_24;

public class Action<T,E extends Enum> {

	private String actionName;


	public Action(String actionName) {
		this.actionName = actionName;
	}
	public  Result<T,E> executeOn(Element e){
		return e.performAction(actionName);
	}

	public static void main(String[] args) {
		Element e = new FireElement("fire");
		Action a = new Action("burn");
		Result<String, FireStatus> rf = a.executeOn(e);
		System.out.println(rf.getValue());

		e = new WaterElement("water");
		a = new Action("freeze");
		Result<Integer,WaterStatus> r = a.executeOn(e);
		System.out.println(r.getValue());

	}

}
