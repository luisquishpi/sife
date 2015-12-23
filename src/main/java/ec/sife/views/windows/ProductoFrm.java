package ec.sife.views.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ec.sife.controllers.PrecioProductoController;
import ec.sife.controllers.ProductoController;
import ec.sife.controllers.TarifaIceController;
import ec.sife.controllers.TarifaIvaController;
import ec.sife.controllers.TipoPrecioController;
import ec.sife.models.entities.CategoriaProducto;
import ec.sife.models.entities.PrecioProducto;
import ec.sife.models.entities.Producto;
import ec.sife.models.entities.TarifaIce;
import ec.sife.models.entities.TarifaIva;
import ec.sife.models.entities.TipoPrecio;
import ec.sife.utils.Formatos;
import ec.sife.utils.UnidadeMedida;

public class ProductoFrm extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtCategoria;
	private JComboBox<UnidadeMedida> cmbUnidadesMedida;
	private JFormattedTextField txtStockMinimo;
	private JButton btnBuscarCategoria;
	private JComboBox<TarifaIva> cmbIva;
	private JComboBox<TarifaIce> cmbIce;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JCheckBox chkFraccionable;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private CategoriaProductoListaFrm categoriaProductoListaFrm = new CategoriaProductoListaFrm();
	private CategoriaProducto categoriaProducto;
	private Producto producto;
	private Producto productoRetorno;
	private PrecioProducto precioProducto = new PrecioProducto();
	private TarifaIva tarifaIva = new TarifaIva();
	private TarifaIce tarifaIce = new TarifaIce();
	List<TipoPrecio> listaTipoPrecio;

	Double porcentaje = 0.0;
	Double subtotal = 0.0;
	Double iva = 0.0;
	Double ice = 0.0;
	Double total = 0.0;
	Double utilidad = 0.0;
	private JTextField txtCodigo;
	private JFormattedTextField txtDescuento;
	private JFormattedTextField txtCosto;
	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblCosto;
	private JLabel lblPrecios;
	private JPanel pnlConfigPrecios;
	private JPanel pnlDatosGenerales;
	private JTabbedPane tabPanel;

	public ProductoFrm() {
		JFrame.setDefaultLookAndFeelDecorated(false);
		producto = new Producto();
		crearControles();
		crearEventos();
		cargarCombos();
		limpiarCampos();
		crearTabla();
		tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
		tarifaIce = (TarifaIce) cmbIce.getSelectedItem();

		Double costoProducto = Double.parseDouble(txtCosto.getText());
		if (costoProducto != 0) {
			cargarTabla(costoProducto, tarifaIva, tarifaIce);
		} else {
			tabPanel.setEnabledAt(1, false);
		}

	}

	private void cargarCombos() {
		CargarComboUnidadMedida();
		CargarComboTarifaIva();
		CargarCombotarifaIce();

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
		listaTipoPrecio = tipoPrecioController.TipoPrecioList();
		System.out.println("Precios: " + listaTipoPrecio);
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
		if (tarifaIva == null || tarifaIce == null)
			return;
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
		Double costoProducto = Double.parseDouble(txtCosto.getText());
		porcentaje = ((subtotal - costoProducto) / costoProducto) * 100;

		System.out.println("total:" + total);
		System.out.println("subtotalConIva: " + subtotalConIva);
		System.out.println("subtotalConIce: " + subtotalConIce);
	}

	private void actualizarValoresTable() {
		for (int i = 0; i < tabla.getRowCount(); i++) {
			porcentaje = Double.parseDouble(modelo.getValueAt(i, 1).toString());
			tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
			tarifaIce = (TarifaIce) cmbIce.getSelectedItem();
			calcularFila(Double.parseDouble(txtCosto.getText()), porcentaje, tarifaIva, tarifaIce);
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

	private void CargarComboUnidadMedida() {
		cmbUnidadesMedida.setModel(new DefaultComboBoxModel<UnidadeMedida>(UnidadeMedida.values()));
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

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombre.setText("");
		txtStockMinimo.setText("0");
		txtDescuento.setText("0");
	}

	private boolean isCamposLlenosNuevo() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCosto.getText().isEmpty()
				|| txtStockMinimo.getText().isEmpty() || txtDescuento.getText().isEmpty()
				|| txtCategoria.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private boolean isNuevoRegistro() {
		return tabPanel.isEnabled();
	}

	private void guardaNuevo() {
		if (!isCamposLlenosNuevo()) {
			JOptionPane.showMessageDialog(null, "No deje campos vacíos");
			return;
		}
		ProductoController productoController = new ProductoController();
		if (productoController.getProducto(txtCodigo.getText()) != null) {
			JOptionPane.showMessageDialog(null, "Código de producto ya está registrado, ingrese otro");
			return;
		}
		;

		guardarProductoNuevo();
		if (producto.getId() != null) {
			Integer cantidadTipoPrecio = 0;
			TipoPrecioController tipoPrecioController = new TipoPrecioController();
			listaTipoPrecio = tipoPrecioController.TipoPrecioList();
			for (TipoPrecio tipoPrecio : listaTipoPrecio) {
				precioProducto = new PrecioProducto();
				guardarPrecioProductoNuevo(tipoPrecio);
				if (precioProducto.getId() != null) {
					cantidadTipoPrecio++;
				}
			}
			if (cantidadTipoPrecio == listaTipoPrecio.size()) {
				JOptionPane.showMessageDialog(null, "Producto guardado correctamente");
				productoRetorno=producto;
				System.out.println("Producto a retornar: " + productoRetorno);
				limpiarCampos();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Error al guardar precios del producto, revise los datos");
				productoController.delete(producto.getId());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error al guardar producto, revise los datos");
		}
	}

	private void guardarPrecioProductoNuevo(TipoPrecio tipoPrecio) {
		precioProducto.setPorcentajeUtilidad(tipoPrecio.getUtilidad());
		precioProducto.setPrecioBruto(0.0);
		precioProducto.setUtilidad(0.0);
		precioProducto.setProducto(producto);
		precioProducto.setTarifaIce(tarifaIce);
		precioProducto.setTarifaIva(tarifaIva);
		precioProducto.setTipoPrecio(tipoPrecio);
		PrecioProductoController precioProductoController = new PrecioProductoController();
		precioProductoController.saveTipoPrecio(precioProducto);
	}

	private void guardarProductoNuevo() {
		ProductoController productoController = new ProductoController();
		producto.setCodigo(txtCodigo.getText());
		producto.setNombre(txtNombre.getText());
		producto.setCategoriaProducto(categoriaProducto);
		producto.setPuedeFraccionar(chkFraccionable.isSelected());
		producto.setUnidadMedida((UnidadeMedida) cmbUnidadesMedida.getSelectedItem());
		producto.setTarifaIva((TarifaIva) cmbIva.getSelectedItem());
		producto.setTarifaIce((TarifaIce) cmbIce.getSelectedItem());
		producto.setStockMinimo(Double.parseDouble(txtStockMinimo.getText()));
		producto.setDescuento(Double.parseDouble(txtDescuento.getText()));
		Date fechaActualizacion = Calendar.getInstance().getTime();
		producto.setFechaActualizacion(fechaActualizacion);
		producto.setCosto(0.0);
		productoController.saveProducto(producto);
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isNuevoRegistro()) {
					guardaNuevo();
				} else {
					// actualizar();
				}

			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		categoriaProductoListaFrm.addConfirmListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriaProducto = categoriaProductoListaFrm.getCategoriaProducto();
				if (categoriaProducto != null)
					txtCategoria.setText(categoriaProducto.getNombre());
			}
		});
		btnBuscarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!categoriaProductoListaFrm.isVisible()) {
					categoriaProductoListaFrm.setModal(true);
					categoriaProductoListaFrm.setVisible(true);
				}
			}
		});
		cmbIva.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				actualizarValoresTable();
			}
		});
		cmbIce.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				actualizarValoresTable();
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 650, 371);
		setTitle("Productos");
		// setClosable(true);
		// setIconifiable(true);
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
		tabPanel = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabPanel, BorderLayout.CENTER);

		pnlDatosGenerales = new JPanel();
		tabPanel.addTab("Datos generales", null, pnlDatosGenerales, null);

		JLabel lblCodigo = new JLabel("Código");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");

		txtNombre = new JTextField();
		txtNombre.setColumns(10);

		JLabel lblCategora = new JLabel("Categoría");

		txtCategoria = new JTextField();
		txtCategoria.setEditable(false);
		txtCategoria.setColumns(10);

		btnBuscarCategoria = new JButton("");

		btnBuscarCategoria.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/search_16.png")));

		JLabel lblUnidadDeMedida = new JLabel("Unidad de medida");

		cmbUnidadesMedida = new JComboBox<UnidadeMedida>();

		chkFraccionable = new JCheckBox("Se puede fraccionar");

		JLabel lblStockMinimo = new JLabel("Stock Mínimo");

		txtStockMinimo = new JFormattedTextField();
		txtStockMinimo.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblIva = new JLabel("Aplicar IVA");

		JLabel lblIce = new JLabel("Aplicar ICE");

		cmbIva = new JComboBox<TarifaIva>();

		cmbIce = new JComboBox<TarifaIce>();

		JLabel lblDescuento = new JLabel("Descuento %");

		txtDescuento = new JFormattedTextField();
		txtDescuento.setText("0");
		GroupLayout gl_pnlDatosGenerales = new GroupLayout(pnlDatosGenerales);
		gl_pnlDatosGenerales
				.setHorizontalGroup(
						gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addContainerGap()
										.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(
														chkFraccionable).addContainerGap())
						.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addGroup(gl_pnlDatosGenerales
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
														.addComponent(lblCodigo).addPreferredGap(
																ComponentPlacement.RELATED, 326, Short.MAX_VALUE))
												.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
														.addComponent(lblNombre).addPreferredGap(
																ComponentPlacement.RELATED, 322, Short.MAX_VALUE))
												.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
														.addGroup(gl_pnlDatosGenerales
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(txtNombre, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
																.addComponent(txtCodigo, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
														.addGap(117))
												.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
														.addGroup(gl_pnlDatosGenerales
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(txtStockMinimo, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
																.addComponent(txtCategoria, GroupLayout.DEFAULT_SIZE,
																		242, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnBuscarCategoria).addGap(62)))
										.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblCategora)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblStockMinimo)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
																.addComponent(lblDescuento).addGap(119))
														.addComponent(txtDescuento, GroupLayout.DEFAULT_SIZE, 170,
																Short.MAX_VALUE)))
										.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
												.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
														.addComponent(lblUnidadDeMedida).addComponent(lblIva))
												.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE))
										.addComponent(cmbIva, 0, 170, Short.MAX_VALUE).addComponent(lblIce)
										.addComponent(cmbIce, 0, 170, Short.MAX_VALUE)
										.addComponent(cmbUnidadesMedida, 0, 170, Short.MAX_VALUE))
								.addGap(90)))));
		gl_pnlDatosGenerales.setVerticalGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addGap(11)
						.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblUnidadDeMedida)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(cmbUnidadesMedida,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblCodigo)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtCodigo,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblIva)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(cmbIva,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblNombre)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtNombre,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup()
										.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblIce).addComponent(lblCategora))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cmbIce, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addGap(20)
										.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
												.addComponent(btnBuscarCategoria).addComponent(txtCategoria,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlDatosGenerales.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblDescuento)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtDescuento,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlDatosGenerales.createSequentialGroup().addComponent(lblStockMinimo)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtStockMinimo,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chkFraccionable)
						.addContainerGap(271, Short.MAX_VALUE)));
		pnlDatosGenerales.setLayout(gl_pnlDatosGenerales);

		pnlConfigPrecios = new JPanel();
		tabPanel.addTab("Configuración de precios", null, pnlConfigPrecios, "");

		txtCosto = new JFormattedTextField();
		txtCosto.setText("0");
		txtCosto.setEditable(false);
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

		scrollPane = new JScrollPane();

		lblCosto = new JLabel("Costo");

		lblPrecios = new JLabel("Precios");
		GroupLayout gl_pnlConfigPrecios = new GroupLayout(pnlConfigPrecios);
		gl_pnlConfigPrecios
				.setHorizontalGroup(gl_pnlConfigPrecios.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlConfigPrecios.createSequentialGroup().addContainerGap()
								.addGroup(gl_pnlConfigPrecios.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCosto).addComponent(lblPrecios)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 609,
												GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCosto, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_pnlConfigPrecios.setVerticalGroup(gl_pnlConfigPrecios.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlConfigPrecios.createSequentialGroup().addContainerGap().addComponent(lblCosto)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtCosto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblPrecios)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(34, Short.MAX_VALUE)));

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
		pnlConfigPrecios.setLayout(gl_pnlConfigPrecios);

	}

	public Producto getProducto() {
		return productoRetorno;
	}

	public void addConfirmListener(ActionListener listener) {
		btnGuardar.addActionListener(listener);
		
	}

	private class CustomCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			rendererComp.setForeground(Color.BLACK);
			rendererComp.setBackground(Color.WHITE);

			return rendererComp;
		}

	}
}
