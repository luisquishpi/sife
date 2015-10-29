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

import ec.sife.controllers.TarifaIceController;
import ec.sife.models.entities.TarifaIce;
import ec.sife.utils.Formatos;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TarifaIceFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JFormattedTextField txtValor;

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

	public TarifaIceFrm() {
		setTitle("Tarifa ICE");
		setIconifiable(true);
		setClosable(true);
		crearControles();
		crearEventos();

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

				TarifaIce tarifaIce = new TarifaIce(txtNombre.getText(), txtCodigo.getText(),
						Double.parseDouble(txtValor.getText()));
				TarifaIceController tarifaIceController = new TarifaIceController();

				if (tarifaIceController.existTarifaIce(tarifaIce.getCodigo())) {
					JOptionPane.showMessageDialog(null, "Ya existe registro con el mismo nombre, ingrese otro");
					return;
				}
				tarifaIceController.saveTarifaIce(tarifaIce);
				if (tarifaIce.getId() != 0) {

					JOptionPane.showMessageDialog(null, "Guardado correctamente");
					limpiarcampos();

				} else {
					JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
				}

			}
		});

	}

	private void crearControles() {
		setBounds(100, 100, 450, 256);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnNuevo = new JButton("Nuevo");

		btnNuevo.setIcon(new ImageIcon(TarifaIceFrm.class.getResource("/ec/sife/images/new.png")));
		panel.add(btnNuevo);

		btnGuardar = new JButton("Guardar");

		btnGuardar.setIcon(new ImageIcon(TarifaIceFrm.class.getResource("/ec/sife/images/save.png")));
		panel.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setIcon(new ImageIcon(TarifaIceFrm.class.getResource("/ec/sife/images/cancel.png")));
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);

		JLabel label = new JLabel("Codigo");

		JLabel label_1 = new JLabel("Nombre");

		txtNombre = new JTextField();
		txtNombre.setColumns(10);

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);

		txtValor = new JFormattedTextField();
		txtValor.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblValor = new JLabel("Porcentaje");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup().addGap(20)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(label)
										.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 226,
												GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblValor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtValor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96,
										Short.MAX_VALUE)))
								.addGap(188)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(11).addComponent(label).addGap(6)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(label_1).addGap(6)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(lblValor).addGap(4).addComponent(txtValor, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		panel_1.setLayout(gl_panel_1);

	}
}
