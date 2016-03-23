package ec.sife.views.windows;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ec.sife.controllers.ProductoController;
import ec.sife.models.entities.Producto;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ProductoListaFrm extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtBuscar;
	private JTable tablaProductos;
	private JScrollPane scrollPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	
	private DefaultTableModel modelo;
	private Object[] filaDatos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoListaFrm dialog = new ProductoListaFrm();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ProductoListaFrm() {
		crearControles();
		crearTablaProductos();

	}

	private void crearTablaProductos() {
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
		cargarDatosEnTablaproductos();
		tablaProductos = new JTable(modelo);
		
		tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaProductos.setPreferredScrollableViewportSize(tablaProductos.getPreferredSize());
		tablaProductos.getTableHeader().setReorderingAllowed(true);
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
		//tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(00);
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(250);
		scrollPane.setViewportView(tablaProductos);

	}

	private void cargarDatosEnTablaproductos() {
		ProductoController productoController = new ProductoController();
		List<Producto> listaProducto = productoController.ProductoList();
		for (Producto producto : listaProducto) {
			modelo.addRow(agregarDatosAFila(producto));
		}
	}
	private Object[] agregarDatosAFila(Producto producto) {
		filaDatos[0] = producto.getId();
		filaDatos[1] = producto.getCodigo();
		filaDatos[2] = producto.getNombre();
		filaDatos[3] = producto.getCategoriaProducto().getNombre();
		return filaDatos;
	}

	

	private void crearControles() {
		setResizable(false);
		setBounds(100, 100, 450, 415);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(ProductoListaFrm.class.getResource("/ec/sife/images/Select.png")));
		panel.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ProductoListaFrm.class.getResource("/ec/sife/images/cancel.png")));
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtBuscar = new JTextField();
		txtBuscar.setPreferredSize(new Dimension(6, 38));
		panel_1.add(txtBuscar);
		txtBuscar.setColumns(20);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(ProductoListaFrm.class.getResource("/ec/sife/images/search.png")));
		panel_1.add(btnBuscar);

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tablaProductos = new JTable();
		scrollPane.setViewportView(tablaProductos);

	}

}
