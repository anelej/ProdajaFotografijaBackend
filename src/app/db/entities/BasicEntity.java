package app.db.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicEntity {

	private long id;
	private List<BasicEntity> foreignKeyEntitiesList;
	protected List<String> columnsName;
	
	public BasicEntity(){
		this.setId(Integer.MIN_VALUE);
		this.columnsName = new ArrayList<String>();
		this.columnsName.add("Id");
	}
	public List<BasicEntity> getForeignKeyEntitiesList() {
		return foreignKeyEntitiesList;
	}

	public void setForeignKeyEntitiesList(List<BasicEntity> foreignKeyEntitiesList) {
		this.foreignKeyEntitiesList = foreignKeyEntitiesList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<String> getColumnsName() {
		return columnsName;
	}
	public void setColumnsName(List<String> columnsName) {
		this.columnsName = columnsName;
	}
	
	public Object getValueForColumnName(String columnName){
		if( "Id".equals(columnName) ){
			return this.id;
		}
		return null;
	}
	
	public void setValueForColumnName(String columnName, Object value){
		if( "Id".equals(columnName) ){
			setId((int) value);
		}
	}	
}
