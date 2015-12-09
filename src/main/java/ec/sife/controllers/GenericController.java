package ec.sife.controllers;

import java.util.List;

import ec.sife.models.entities.Producto;

public interface GenericController {
	
	public void saveProducto(Producto producto);
	public boolean existProducto(Producto producto);
	public List<Producto> ProductoList();
	public Producto getProducto(int id);
	public boolean update(Producto producto);
	public boolean delete(Integer id);
}
