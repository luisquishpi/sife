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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import ec.sife.controllers.TipoIdentificacionPersonaController;
import ec.sife.models.entities.TipoIdentificacionPersona;
public class TipoIdentificacionPersonaFrm extends JInternalFrame {

	
	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel lblId;

	
	public TipoIdentificacionPersonaFrm() {
		
		crearControles();
		crearEventos();
		
	}

	private void limpiarcampos() {
				     

		lblId.setText("0");
		txtNombre.setText("");
		txtCodigo.setText("");
		
		txtCodigo.requestFocus();
		
	}
	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos()
	{
		// TODO Auto-generated method stub
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarcampos();
			}
	
		});
		
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
				if (lblId.getText().toString() == "0") {

					TipoIdentificacionPersona tipoIdentificacionPersona= new TipoIdentificacionPersona(txtNombre.getText(),txtCodigo.getText());
					TipoIdentificacionPersonaController tipoIdentificacionPersonaController=new TipoIdentificacionPersonaController();
					if (tipoIdentificacionPersonaController.existTipoIdentificacionPersona(tipoIdentificacionPersona.getNombre())) {
	                    JOptionPane.showMessageDialog(null,
	                            "Ya existe registro con el mismo nombre, ingrese otro");
	                    return;
	                }
					tipoIdentificacionPersonaController.saveTipoIdentificacionPersona(tipoIdentificacionPersona);
					if (tipoIdentificacionPersona.getId() != 0) { 
						
						 JOptionPane.showMessageDialog(null, "Guardado correctamente");
						 limpiarcampos();
		                   
		                } else {
		                    JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
		                }						
				}			
				
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	private void crearControles() {
		
		setBounds(100, 100, 450, 199);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnNuevo = new JButton("Nuevo");
		
		btnNuevo.setIcon(new ImageIcon("C:\\Users\\Celia\\Desktop\\workspacemars\\sife\\target\\classes\\ec\\sife\\images\\new.png"));
		panel.add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		
		btnGuardar.setIcon(new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/save.png")));
		panel.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		
		btnCancelar.setIcon(new ImageIcon("C:\\Users\\Celia\\Desktop\\workspacemars\\sife\\target\\classes\\ec\\sife\\images\\cancel.png"));
		panel.add(btnCancelar);
		
				
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.WEST);
		
		JLabel lblCodigo = new JLabel("Codigo");
		
		JLabel label_1 = new JLabel("Nombre");
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		
		lblId = new JLabel("0");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblCodigo)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
							.addGap(2)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblId)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addGap(171))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(6)
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCodigo)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
	}
}
