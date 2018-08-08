package app.db.controllers;

import java.util.List;
import app.db.entities.BasicEntity;

public interface IDatabaseController {
	boolean add(BasicEntity object);
	boolean removeById(int id);
	boolean update(BasicEntity object);
	List<BasicEntity> getAll();
	BasicEntity getById(int id);
}
