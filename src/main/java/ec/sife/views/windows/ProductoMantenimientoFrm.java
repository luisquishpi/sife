package ec.sife.views.windows;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ec.sife.controllers.PrecioProductoController;
import ec.sife.controllers.ProductoController;
import ec.sife.models.entities.Producto;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import java.awt.Font;

public class ProductoMantenimientoFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private JTable tablaProductos;
	private Object[] filaDatos;
	private JScrollPane scrollPane;
	private ProductoFrm productoFrm = new ProductoFrm();
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private Producto producto;
	private JButton btnBuscar;
	private JTextField txtBuscar;

	public ProductoMantenimientoFrm() {
		setClosable(true);
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Código", "Nombre", "Categoría" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tablaProductos = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaProductos.setPreferredScrollableViewportSize(tablaProductos.getPreferredSize());
		tablaProductos.getTableHeader().setReorderingAllowed(true);
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(150);
		scrollPane.setViewportView(tablaProductos);

	}

	private Object[] agregarDatosAFila(Producto producto) {
		filaDatos[0] = producto.getId();
		filaDatos[1] = producto.getCodigo();
		filaDatos[2] = producto.getNombre();
		filaDatos[3] = producto.getCategoriaProducto().getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		ProductoController productoController = new ProductoController();
		List<Producto> listaProducto = productoController.ProductoList();
		for (Producto producto : listaProducto) {
			modelo.addRow(agregarDatosAFila(producto));
		}

	}

	private void capturaYAgregaProductoATabla() {
		producto = new Producto();
		producto = productoFrm.getProducto();
		if (producto != null && producto.getId() != null) {
			System.out.println("Captura Producto retornado: " + producto);
			modelo.addRow(agregarDatosAFila(producto));
			tablaProductos.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	private void crearEventos() {
		productoFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaProductoATabla();
			}

		});
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!productoFrm.isVisible()) {
					productoFrm.setModal(true);
					productoFrm.setVisible(true);
				}
			}
		});
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProductoController productoController = new ProductoController();
				List<Producto> listaProducto = productoController.ProductoList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Producto producto : listaProducto) {
					modelo.addRow(agregarDatosAFila(producto));
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProductoController productoController = new ProductoController();
				int fila=tablaProductos.getSelectedRow();
				int idProducto = (Integer) modelo.getValueAt(fila, 0);
				try {
					productoController.delete(idProducto);
				} catch (Exception ex) {
					try {
						PrecioProductoController precioProductoController = new PrecioProductoController();
						Producto productoRemove = new Producto();
						productoRemove.setId(idProducto);
						boolean retorno = precioProductoController.deletePreciosPorIdProducto(productoRemove);
						if (retorno == true) {
							System.out.println("Eliminado precios");
							productoController.delete(idProducto);
							modelo.removeRow(fila);
							modelo.fireTableDataChanged();
							System.out.println("Eliminado producto");
							//y si esta relacionado con otras entidades? ya no se elimina el producto pero quedó sin precios
							//ERROR!!
						}
					} catch (Exception ex2) {
						System.out.println("Error1: " + ex + "Error2: " + ex2);
					}

				}

			}
		});

	}

	private void crearControles() {
		setTitle("Mantenimiento de Productos");
		setIconifiable(true);
		setBounds(100, 100, 680, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel panelR = new JPanel();
		panelR.setPreferredSize(new Dimension(120, 0));
		getContentPane().add(panelR, BorderLayout.EAST);
		panelR.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtBuscar = new JTextField();
		txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(txtBuscar);
		txtBuscar.setPreferredSize(new Dimension(420, 38));

		btnBuscar = new JButton("Buscar");

		btnBuscar.setIcon(new ImageIcon(ProductoMantenimientoFrm.class.getResource("/ec/sife/images/search.png")));
		panel.add(btnBuscar);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setPreferredSize(new Dimension(110, 45));
		btnAgregar.setIcon(new ImageIcon(ProductoMantenimientoFrm.class.getResource("/ec/sife/images/new.png")));
		panelR.add(btnAgregar);

		btnEditar = new JButton("Editar");
		btnEditar.setPreferredSize(new Dimension(110, 45));
		btnEditar.setIcon(new ImageIcon(ProductoMantenimientoFrm.class.getResource("/ec/sife/images/edit.png")));
		panelR.add(btnEditar);

		btnEliminar = new JButton("Eliminar");

		btnEliminar.setPreferredSize(new Dimension(110, 45));
		btnEliminar.setIcon(new ImageIcon(ProductoMantenimientoFrm.class.getResource("/ec/sife/images/delete.png")));
		panelR.add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

	}

}
