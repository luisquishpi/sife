package ec.sife.views.windows;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ec.sife.controllers.TarifaIvaController;
import ec.sife.models.entities.TarifaIva;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ProductoFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JComboBox cmbUnidadesMedida;
	private JFormattedTextField formattedTextField;
	private JFormattedTextField txtCantidadMinima;
	private JButton btnBuscar;
	private JComboBox cmbIva;
	private JComboBox cmbIce;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JCheckBox chckbxFraccionable;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoFrm frame = new ProductoFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProductoFrm() {
		crearControles();
		cargarCombos();
		crearEventos();
	}

	private void cargarCombos() {
		TarifaIvaController tarifaIvaController=new TarifaIvaController();
		List<TarifaIva> listaTarifaIva;
		//listaTarifaIva=tarifaIvaController.TarifaIvaList();
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
		setBounds(100, 100, 550, 424);
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

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");

		textField_1 = new JTextField();
		textField_1.setColumns(10);

		JLabel lblCategora = new JLabel("Categoría");

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);

		btnBuscar = new JButton("");
		btnBuscar.setIcon(new ImageIcon(ProductoFrm.class.getResource("/ec/sife/images/search_16.png")));

		JLabel lblUnidadDeMedida = new JLabel("Unidad de medida");

		cmbUnidadesMedida = new JComboBox();

		chckbxFraccionable = new JCheckBox("Fraccionable");

		JLabel lblStockMinimo = new JLabel("Stock Mínimo");

		txtCantidadMinima = new JFormattedTextField();

		JLabel lblCosto = new JLabel("Costo");

		formattedTextField = new JFormattedTextField();

		table = new JTable();

		JLabel lblIva = new JLabel("IVA");

		JLabel lblIce = new JLabel("ICE");

		cmbIva = new JComboBox();

		cmbIce = new JComboBox();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addComponent(table, GroupLayout.DEFAULT_SIZE, 514,
								Short.MAX_VALUE)
						.addContainerGap()).addGroup(
								gl_panel.createSequentialGroup()
										.addGroup(
												gl_panel.createParallelGroup(Alignment.LEADING, false)
														.addComponent(textField_2)
														.addComponent(textField, GroupLayout.DEFAULT_SIZE, 183,
																Short.MAX_VALUE)
														.addComponent(lblCdigoDeBarra).addComponent(lblNombre)
														.addComponent(textField_1).addComponent(lblCategora))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBuscar)
										.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblUnidadDeMedida)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
														.addComponent(cmbUnidadesMedida, 0, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(gl_panel.createSequentialGroup()
																.addComponent(lblCosto).addGap(98)
																.addComponent(lblStockMinimo).addGap(33)))
												.addGroup(gl_panel.createSequentialGroup()
														.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
																.addComponent(formattedTextField,
																		GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
																.addComponent(lblIva)
																.addComponent(cmbIva, 0, GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(chckbxFraccionable))
														.addGap(18)
														.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
																.addComponent(lblIce)
																.addComponent(txtCantidadMinima,
																		GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
																.addComponent(cmbIce, 0, GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))))
										.addGap(27)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(btnBuscar))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup().addGap(20).addComponent(lblCdigoDeBarra)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(11).addComponent(lblNombre).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblCategora)
										.addGap(7).addComponent(textField_2, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(
										gl_panel.createSequentialGroup().addContainerGap()
												.addGroup(gl_panel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel.createSequentialGroup().addGap(51)
																		.addGroup(gl_panel
																				.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblCosto)
																				.addComponent(lblStockMinimo))
														.addPreferredGap(
																ComponentPlacement.RELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
														.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtCantidadMinima, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_panel.createSequentialGroup().addComponent(lblUnidadDeMedida)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(cmbUnidadesMedida, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED, 21,
																Short.MAX_VALUE)
														.addComponent(lblIva)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(cmbIva, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup().addGap(18).addComponent(lblIce)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(cmbIce, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))))))
						.addGap(18).addComponent(chckbxFraccionable).addGap(18)
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		panel.setLayout(gl_panel);

	}

}
