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
import ec.sife.controllers.TipoClienteController;
import ec.sife.models.entities.TipoCliente;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TipoClienteFrm extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JTextField txtNombre;
	 private TipoClienteController tipoClienteController;
	 private JButton btnNuevo;
	/**
	 * Launch the application.
	 */
	
	 public void limpiarCampos() {
	        txtNombre.setText("");
	        txtNombre.requestFocus();
	        
	    }

	    private boolean isCamposLlenos() {
	        boolean llenos = true;
	        if (txtNombre.getText().isEmpty())
	            llenos = false;
	        return llenos;
	    }

	/**
	 * Create the frame.
	 */
	   
	public TipoClienteFrm() {
		crearControles();
		crearEventos();

	}

	private void crearEventos() {
		// TODO Auto-generated method stub
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tipoClienteController = new TipoClienteController();
				
                if (!isCamposLlenos()) {
                    JOptionPane.showMessageDialog(null, "No deje campos vacíos");
                    return;
                }
                TipoCliente tipoCliente = new TipoCliente(txtNombre.getText());
                
               
                if (tipoClienteController.existTipoCliente(tipoCliente.getNombre())) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existe registro con el mismo nombre, ingrese otro");
                    return;
                }
                
                tipoClienteController.saveTipoCliente(tipoCliente);
                if (tipoCliente.getId() != null) {
                    JOptionPane.showMessageDialog(null, "Guardado correctamente");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
                }
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});
		
	}

	private void crearControles() {
		setTitle("Tipo de Cliente");
		setBounds(100, 100, 450, 171);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnNuevo = new JButton("Nuevo");
		
		btnNuevo.setIcon(new ImageIcon("C:\\Users\\Celia\\Desktop\\workspacemars\\sife\\target\\classes\\ec\\sife\\images\\new.png"));
		panel.add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		
		btnGuardar.setIcon(new ImageIcon("C:\\Users\\Celia\\Desktop\\workspacemars\\sife\\target\\classes\\ec\\sife\\images\\save.png"));
		panel.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		
		btnCancelar.setIcon(new ImageIcon("C:\\Users\\Celia\\Desktop\\workspacemars\\sife\\target\\classes\\ec\\sife\\images\\cancel.png"));
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel label_1 = new JLabel("Nombre");
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(76, Short.MAX_VALUE)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addGap(112))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
	}

}
