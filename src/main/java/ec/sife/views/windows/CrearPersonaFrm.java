package ec.sife.views.windows;


import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ec.sife.controllers.PersonaController;

import ec.sife.models.entities.Persona;

import ec.sife.controllers.TipoIdentificacionPersonaController;
import ec.sife.models.entities.TipoIdentificacionPersona;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearPersonaFrm extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtCelular;
	private JTextField txtMail;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtRazonSocial;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoIdentificacion;
	private JTextField txtIdentificacion;
	private JButton BtnGuardar;
	private PersonaController  personaController;
	private JButton btnCancelar;
	private JButton btnNuevo;

	
	public CrearPersonaFrm() {
		
		crearControles();
		crearEventos();
		llenarTipoIdentifiacion();
		
		
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void llenarTipoIdentifiacion()
	{
		TipoIdentificacionPersonaController tipoIdentificacionPersonaCotroller= new TipoIdentificacionPersonaController();
		List<TipoIdentificacionPersona> lista;		
		lista = tipoIdentificacionPersonaCotroller.TipoIdentificacionPersonaList();
		cmbTipoIdentificacion.setModel(new DefaultComboBoxModel (lista.toArray()));
		
	
	}
	private void limpiarCampos()
	{
		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtMail.setText("");
		txtIdentificacion.requestFocus();		
		
	}
	 private boolean isCamposLlenos() {
	        boolean llenos = true;
	        if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty())
	            llenos = false;
	        return llenos;
	    }

	private void crearEventos() {
		BtnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				personaController= new PersonaController();
				
				
                if (!isCamposLlenos()) {
                    JOptionPane.showMessageDialog(null, "No deje campos vacíos");
                    return;
                }
                
                TipoIdentificacionPersona tipoIdentificacionPersona= (TipoIdentificacionPersona) cmbTipoIdentificacion.getSelectedItem();               
                Persona persona = new Persona(tipoIdentificacionPersona,txtIdentificacion.getText(),txtRazonSocial.getText(), txtDireccion.getText(),txtTelefono.getText(),txtCelular.getText(),txtMail.getText());
                
              
                if (personaController.existPersona(persona.getIdentificacion())) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existe registro con el mismo nombre, ingrese otro");
                    return;
                }
                
                personaController.savePersona(persona);
                if (persona.getId() != null) {
                    JOptionPane.showMessageDialog(null, "Guardado correctamente");
                    limpiarCampos();
                    } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
                }
				
				
			}
		});
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});
	}


	private void crearControles() {
		setTitle("Persona");
		setBounds(100, 100, 524, 326);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnNuevo = new JButton("Nuevo");
		
		btnNuevo.setIcon(new ImageIcon(CrearPersonaFrm.class.getResource("/ec/sife/images/new.png")));
		panel.add(btnNuevo);
		
		BtnGuardar = new JButton("Guardar");
		
		BtnGuardar.setIcon(new ImageIcon(CrearPersonaFrm.class.getResource("/ec/sife/images/save.png")));
		panel.add(BtnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		
		btnCancelar.setIcon(new ImageIcon(CrearPersonaFrm.class.getResource("/ec/sife/images/cancel.png")));
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JLabel lblIdentificacin = new JLabel("Identificación");
		
		txtIdentificacion = new JTextField();
		txtIdentificacion.setColumns(10);
		
		JLabel lblTipoDeIdentificaci = new JLabel("Tipo de Identificación");
		
		cmbTipoIdentificacion = new JComboBox<TipoIdentificacionPersona>();
		
		JLabel lblRaznSocial = new JLabel("Razón Social");
		
		txtRazonSocial = new JTextField();
		txtRazonSocial.setColumns(10);
		
		JLabel lblDireccin = new JLabel("Dirección");
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		
		JLabel lblCelular = new JLabel("Celular");
		
		txtCelular = new JTextField();
		txtCelular.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail");
		
		txtMail = new JTextField();
		txtMail.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTipoDeIdentificaci)
								.addComponent(cmbTipoIdentificacion, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(txtIdentificacion, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblIdentificacin, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
							.addGap(118))
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblCelular, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
										.addGroup(Alignment.LEADING, gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(txtCelular, Alignment.LEADING)
											.addComponent(lblDireccin, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtDireccion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
									.addGap(26)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(txtMail, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
										.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTelfono, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtTelefono, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
								.addGroup(Alignment.LEADING, gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addComponent(lblRaznSocial, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtRazonSocial, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)))
							.addGap(118))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblTipoDeIdentificaci)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbTipoIdentificacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblIdentificacin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtIdentificacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblRaznSocial)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtRazonSocial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDireccin)
						.addComponent(lblTelfono))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtDireccion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCelular)
						.addComponent(lblEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCelular, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(148, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);		
	}
}
