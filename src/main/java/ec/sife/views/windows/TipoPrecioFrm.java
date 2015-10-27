package ec.sife.views.windows;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.sife.controllers.TipoPrecioController;
import ec.sife.models.entities.TipoPrecio;
import ec.sife.utils.Formatos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;

public class TipoPrecioFrm extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNombre;

    private JFormattedTextField txtUtilidad;

    private TipoPrecioController tipoPrecioController;

    private JButton btnGuardar;

    private JButton btnNuevo;

    private JButton btnCancelar;

    public TipoPrecioFrm() {
        setTitle("Tipo de Precios");
        crearControles();
        crearEventos();

    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtUtilidad.setText("0");
    }

    private boolean isCamposLlenos() {
        boolean llenos = true;
        if (txtNombre.getText().isEmpty() || txtUtilidad.getText().isEmpty())
            llenos = false;
        return llenos;
    }

    private void crearEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoPrecioController = new TipoPrecioController();
                if (!isCamposLlenos()) {
                    JOptionPane.showMessageDialog(null, "No deje campos vacíos");
                    return;
                }
                TipoPrecio tipoPrecio = new TipoPrecio(txtNombre.getText(),
                        Double.parseDouble(txtUtilidad.getText()));
                if (tipoPrecioController.existTipoPrecio(tipoPrecio.getNombre())) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existe registro con el mismo nombre, ingrese otro");
                    return;
                }
                tipoPrecioController.saveTipoPrecio(tipoPrecio);
                if (tipoPrecio.getId() != null) {
                    JOptionPane.showMessageDialog(null, "Guardado correctamente");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar, revise los datos");
                }
            }
        });
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    private void crearControles() {
        setIconifiable(true);
        setClosable(true);
        setBounds(100, 100, 400, 217);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        btnNuevo = new JButton("Nuevo");

        btnNuevo.setIcon(new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/new.png")));
        panel.add(btnNuevo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setIcon(
                new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/save.png")));

        panel.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");

        btnCancelar.setIcon(
                new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/cancel.png")));
        panel.add(btnCancelar);

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.NORTH);

        JLabel label = new JLabel("Nombre");

        txtNombre = new JTextField();
        txtNombre.setColumns(10);

        JLabel lblUtilidad = new JLabel("Utilidad %");

        txtUtilidad = new JFormattedTextField();
        txtUtilidad.setFormatterFactory(new Formatos().getDecimalFormat());

        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addComponent(label, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addComponent(lblUtilidad, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGap(317))
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addComponent(txtUtilidad, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())))
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addGap(14)
        			.addComponent(label)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(lblUtilidad)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtUtilidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(32, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);

    }
}
