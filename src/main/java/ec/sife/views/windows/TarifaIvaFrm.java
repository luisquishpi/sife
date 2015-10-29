package ec.sife.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import ec.sife.controllers.TarifaIvaController;
import ec.sife.models.entities.TarifaIva;
import ec.sife.utils.Formatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class TarifaIvaFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnNuevo;
	private JLabel lblValor;
	private JFormattedTextField txtValor;

	public TarifaIvaFrm() {
		setTitle("Tarifa IVA");
		setIconifiable(true);
		setClosable(true);

		crearControles();
		crearEventos();

	}

	private void limpiarcampos() {

		txtNombre.setText("");
		txtCodigo.setText("");
		txtValor.setText("0");
		txtCodigo.requestFocus();

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtValor.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarcampos();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
				TarifaIva tarifaIva = new TarifaIva(txtNombre.getText(), txtCodigo.getText(), Double.parseDouble(txtValor.getText()));
				TarifaIvaController tarifaIvaController = new TarifaIvaController();
				if (tarifaIvaController.existTarifaIva(tarifaIva.getCodigo())) {
					JOptionPane.showMessageDialog(null, "Ya existe registro con el mismo nombre, ingrese otro");
					return;
				}
				tarifaIvaController.saveTarifaIva(tarifaIva);
				if (tarifaIva.getId() != 0) {

					JOptionPane.showMessageDialog(null, "Guardado correctamente");
					limpiarcampos();

				} else {
					JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
				}

			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 450, 252);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnNuevo = new JButton("Nuevo");

		btnNuevo.setIcon(new ImageIcon(TarifaIvaFrm.class.getResource("/ec/sife/images/new.png")));
		panel.add(btnNuevo);

		btnGuardar = new JButton("Guardar");

		btnGuardar.setIcon(new ImageIcon(TarifaIvaFrm.class.getResource("/ec/sife/images/save.png")));
		panel.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setIcon(new ImageIcon(TarifaIvaFrm.class.getResource("/ec/sife/images/cancel.png")));
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);

		JLabel label = new JLabel("Codigo");

		JLabel label_1 = new JLabel("Nombre");

		txtNombre = new JTextField();
		txtNombre.setColumns(10);

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);

		lblValor = new JLabel("Valor");

		txtValor = new JFormattedTextField();
		txtValor.setFormatterFactory(new Formatos().getDecimalFormat());
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(19)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 102,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1
								.createParallelGroup(Alignment.LEADING).addComponent(label)
								.addGroup(gl_panel_1.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
												.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 185,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblValor).addComponent(txtValor,
														GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))))
								.addGap(362)))));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(6).addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(label_1).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(lblValor).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtValor,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(24, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

	}
}
