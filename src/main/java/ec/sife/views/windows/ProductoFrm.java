package ec.sife.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ec.sife.controllers.TarifaIceController;
import ec.sife.controllers.TarifaIvaController;
import ec.sife.controllers.TipoPrecioController;
import ec.sife.models.entities.TarifaIce;
import ec.sife.models.entities.TarifaIva;
import ec.sife.models.entities.TipoPrecio;
import ec.sife.utils.Formatos;
import ec.sife.utils.UnidadeMedida;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ProductoFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtCodigoBarra;
	private JTextField txtNombre;
	private JTextField txtCategoria;
	private JTable tabla;
	private JComboBox<UnidadeMedida> cmbUnidadesMedida;
	private JFormattedTextField txtCosto;
	private JFormattedTextField txtStockMinimo;
	private JButton btnBuscar;
	private JComboBox<TarifaIva> cmbIva;
	private JComboBox<TarifaIce> cmbIce;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JCheckBox chkFraccionable;
	private DefaultTableModel modelo;
	private Object[] filaDatos;

	Double porcentaje = 0.0;
	Double subtotal = 0.0;
	Double iva = 0.0;
	Double ice = 0.0;
	Double total = 0.0;
	Double utilidad = 0.0;

	public ProductoFrm() {
		crearControles();
		crearEventos();
		cargarCombos();
		txtCosto.setText("0");
		txtStockMinimo.setText("0");
		crearTabla();

		Double costoProducto = Double.parseDouble(txtCosto.getText());
		TarifaIva tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
		TarifaIce tarifaIce = (TarifaIce) cmbIce.getSelectedItem();

		cargarTabla(costoProducto, tarifaIva, tarifaIce);
	}

	private void crearTabla() {
		Object[] cabecera = { "Tipo Precio", "Porcentaje", "Subtotal", "ICE", "IVA", "Total", "Utilidad" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4 || columnIndex == 6) {
					return false;
				}
				return true;
			}
		};
		tabla.setModel(modelo);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(130);

		filaDatos = new Object[cabecera.length];

	}

	private void cargarTabla(Double costoProducto, TarifaIva tarifaIva, TarifaIce tarifaIce) {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio = tipoPrecioController.TipoPrecioList();

		for (TipoPrecio tipoPrecio : listaTipoPrecio) {
			porcentaje = tipoPrecio.getUtilidad();
			calcularFila(costoProducto, porcentaje, tarifaIva, tarifaIce);
			filaDatos[0] = tipoPrecio.getNombre();
			filaDatos[1] = porcentaje;
			filaDatos[2] = subtotal;
			filaDatos[3] = iva;
			filaDatos[4] = ice;
			filaDatos[5] = total;
			filaDatos[6] = utilidad;
			modelo.addRow(filaDatos);
		}

	}

	private void calcularFila(Double costoProducto, Double porcentajeAplicar, TarifaIva tarifaIva,
			TarifaIce tarifaIce) {
		subtotal = (costoProducto * (porcentaje / 100)) + costoProducto;
		ice = subtotal * (tarifaIce.getValor() / 100);
		iva = (subtotal + ice) * (tarifaIva.getValor() / 100);
		total = subtotal + ice + iva;
		utilidad = subtotal - costoProducto;
	}

	private void calcularFilaInversa(Double total, TarifaIva tarifaIva, TarifaIce tarifaIce) {
		Double subtotalConIva = total / ((tarifaIva.getValor() / 100) + 1);
		iva = total - subtotalConIva;
		Double subtotalConIce = subtotalConIva / ((tarifaIce.getValor() / 100) + 1);
		ice = subtotalConIva - subtotalConIce;
		subtotal = subtotalConIce;
		Double costoProducto=Double.parseDouble(txtCosto.getText());
		porcentaje=((subtotal-costoProducto)/costoProducto)*100;
		
		System.out.println("total:" + total);
		System.out.println("subtotalConIva: " + subtotalConIva);
		System.out.println("subtotalConIce: " + subtotalConIce);
	}

	private void actualizarValoresTable() {
		for (int i = 0; i < tabla.getRowCount(); i++) {
			porcentaje = Double.parseDouble(modelo.getValueAt(i, 1).toString());
			calcularFila(Double.parseDouble(txtCosto.getText()), porcentaje, (TarifaIva) cmbIva.getSelectedItem(),
					(TarifaIce) cmbIce.getSelectedItem());
			modelo.setValueAt(subtotal, i, 2);
			modelo.setValueAt(ice, i, 3);
			modelo.setValueAt(iva, i, 4);
			modelo.setValueAt(total, i, 5);
			modelo.setValueAt(utilidad, i, 6);
		}

	}

	private void actualizarValoresTableInversa() {
		for (int i = 0; i < tabla.getRowCount(); i++) {
			total = Double.parseDouble(modelo.getValueAt(i, 5).toString());
			calcularFilaInversa(total, (TarifaIva) cmbIva.getSelectedItem(), (TarifaIce) cmbIce.getSelectedItem());
			modelo.setValueAt(porcentaje, i, 1);
			modelo.setValueAt(subtotal, i, 2);
			modelo.setValueAt(ice, i, 3);
			modelo.setValueAt(iva, i, 4);
			modelo.setValueAt(utilidad, i, 6);
		}

	}

	private void cargarCombos() {
		CargarComboTarifaIva();
		CargarCombotarifaIce();
		CargarComboUnidadMedida();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarComboUnidadMedida() {
		cmbUnidadesMedida.setModel(new DefaultComboBoxModel(UnidadeMedida.values()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarCombotarifaIce() {
		TarifaIceController tarifaIvaController = new TarifaIceController();
		List<TarifaIce> listaTarifaIce;
		listaTarifaIce = tarifaIvaController.TarifaIceList();
		cmbIce.setModel(new DefaultComboBoxModel(listaTarifaIce.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarComboTarifaIva() {
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva;
		listaTarifaIva = tarifaIvaController.TarifaIvaList();
		cmbIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));

	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 650, 491);
		setTitle("Productos");
		setClosable(true);
		setIconifiable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelBotones = new JPanel();
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/new.png")));
		panelBotones.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/save.png")));
		panelBotones.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/cancel.png")));
		panelBotones.add(btnCancelar);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblCdigoDeBarra = new JLabel("Código de barra");

		txtCodigoBarra = new JTextField();
		txtCodigoBarra.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");

		txtNombre = new JTextField();
		txtNombre.setColumns(10);

		JLabel lblCategora = new JLabel("Categoría");

		txtCategoria = new JTextField();
		txtCategoria.setEditable(false);
		txtCategoria.setColumns(10);

		btnBuscar = new JButton("");
		btnBuscar.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/search_16.png")));

		JLabel lblUnidadDeMedida = new JLabel("Unidad de medida");

		cmbUnidadesMedida = new JComboBox<UnidadeMedida>();

		chkFraccionable = new JCheckBox("Fraccionable");

		JLabel lblStockMinimo = new JLabel("Stock Mínimo");

		txtStockMinimo = new JFormattedTextField();
		txtStockMinimo.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblCosto = new JLabel("Costo");

		txtCosto = new JFormattedTextField();
		txtCosto.setFormatterFactory(new Formatos().getDecimalFormat());
		DocumentListener documentListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void removeUpdate(DocumentEvent documentEvent) {
			}

			private void printIt(DocumentEvent documentEvent) {
				actualizarValoresTable();
			}
		};
		txtCosto.getDocument().addDocumentListener(documentListener);

		JLabel lblIva = new JLabel("Aplicar IVA");

		JLabel lblIce = new JLabel("Aplicar ICE");

		cmbIva = new JComboBox<TarifaIva>();
		cmbIva.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				actualizarValoresTable();
			}
		});

		cmbIce = new JComboBox<TarifaIce>();
		cmbIce.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				actualizarValoresTable();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(lblCategora)
						.addContainerGap(577, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE).addContainerGap())
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(10).addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING).addComponent(lblCdigoDeBarra)
								.addComponent(lblNombre).addComponent(lblCosto)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(txtCosto, GroupLayout.PREFERRED_SIZE, 112,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(chkFraccionable))
								.addComponent(txtNombre, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
								.addComponent(txtCodigoBarra, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(txtCategoria, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBuscar)))
						.addGap(117)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
														.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																.addComponent(lblUnidadDeMedida).addComponent(lblIva))
												.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE))
										.addComponent(cmbIva, 0, 121, Short.MAX_VALUE).addComponent(lblIce)
										.addComponent(cmbIce, 0, 121, Short.MAX_VALUE)
										.addComponent(txtStockMinimo, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
										.addComponent(cmbUnidadesMedida, 0, 121, Short.MAX_VALUE)).addGap(112))
								.addComponent(lblStockMinimo))
						.addGap(36)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblUnidadDeMedida)
								.addComponent(lblCdigoDeBarra))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbUnidadesMedida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCodigoBarra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblIva)
								.addComponent(lblNombre))
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cmbIva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblCategora).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBuscar)))
						.addGroup(gl_panel.createSequentialGroup().addComponent(lblIce)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(cmbIce,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblCosto)
										.addComponent(lblStockMinimo))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(txtCosto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtStockMinimo, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(chkFraccionable)).addGap(32)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE).addContainerGap()));

		final CustomCellRenderer renderer = new CustomCellRenderer();
		tabla = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return renderer;
			}
		};
		CellEditorListener changeNotification = new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				if (tabla.getSelectedColumn() == 5) {
					// System.out.println("Aun por construir");
					actualizarValoresTableInversa();
				} else {
					actualizarValoresTable();
				}
			}

			public void editingCanceled(ChangeEvent arg0) {
			}
		};
		tabla.getDefaultEditor(String.class).addCellEditorListener(changeNotification);

		scrollPane.setViewportView(tabla);
		panel.setLayout(gl_panel);

	}

	private class CustomCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			rendererComp.setForeground(Color.WHITE);
			rendererComp.setBackground(Color.DARK_GRAY);

			return rendererComp;
		}

	}

}
