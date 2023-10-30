package Main;
import java.awt.Color;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ToDoList extends javax.swing.JFrame {

    public ToDoList() {
        initComponents();
        setLocationRelativeTo(null);
    }
/********************************METODOS************************************************/
    public void toList() {
        //Definir la sentencia SQL para seleccionar todos los registros de la tabla tareas

        String sql = "SELECT * FROM tarea";

        //Creamos una instancia de la clase main para establecer la conexion a la base de datos
        Main con = new Main();

        Connection conexion = con.establecerConeccion();

        System.out.println(sql);

        //Creamos un modelo de tabla para almacenar los datos
        DefaultTableModel model = new DefaultTableModel();

        try {

            //Creamos una declaración para ejecutar la consulta SQL
            Statement st = conexion.createStatement();

            ResultSet rs = st.executeQuery(sql);

            //Obtenemos la información de las colunmas de la consulta
            ResultSetMetaData metaData = rs.getMetaData();

            int numColumnas = metaData.getColumnCount();

            for (int column = 1; column <= numColumnas; column++) {
                model.addColumn(metaData.getColumnName(column));
            }

            //Se agregan filas al modelo de la tabla
            while (rs.next()) {
                Object[] rowData = new Object[numColumnas];
                for (int i = 0; i < numColumnas; i++) {
                    //Obtenemos los datos de cada columna por indice (comience en 1)
                    rowData[i] = rs.getObject(i + 1);
                }
                model.addRow(rowData);
            }

            //Asignamos el modelo de tabla al componente TablaDatos
            tblTasks.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addTask(String txtTarea,String txtEstado){
        //Definimos la sentencia sql para insertar una nueva carrera con el nombre proporcionado
        String sql = "INSERT INTO tarea (DESCRIPTION,STATE) VALUES (?,?)";

        //Creamos una instancia de la clase Main para establecer la conexión a la base de datos
        Main con = new Main();

        //Establecemos la conexión a la base de datos
        Connection conexion = con.establecerConeccion();
        

        

        // Luego, conviertes la fecha al tipo java.sql.Date
   
        try {

            //Preparamos la sentencia SQL para su ejecución
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);

            //Establecemos el valor del parámetro en la sentencia sql 
            preparedStatement.setString(1,txtTarea);
            preparedStatement.setString(2,txtEstado);
            

            //Ejecutar la sentencia sql y obtenemos el número de filas afectadas
            int filasAfectadas = preparedStatement.executeUpdate();

            //Comprobamos si se agregó o no el registro a nuestra BD
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Tarea Agregada");
            } else {
                JOptionPane.showMessageDialog(null, "ErroR al agregar tarea, por favor vuelva a intentarlo");
            }

            //Cerramos la declaración preparada
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void nuevo() {
        txtTarea.setText("");
        txtEstado.setText("");
        txtTarea.requestFocus();
    }
    
    public int getIdSelected() {
        //Obetener la fila seleccionada en la tablas
        int filaSeleccionada = tblTasks.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
            return -1; //Retornamos un valor negativo para indicar que no se ha seleccionado nada
        }

        int id = (int) tblTasks.getValueAt(filaSeleccionada, 0);
        return id;
    }
    
    public void edit(){
           //Obtener el nuevo nombnre de la tarea desde un campo de texto
        String nuevaTarea = txtTarea.getText();

        String nuevoEstado = txtEstado.getText();
        //Verificar si se proporcionó un nuevo nombre
        if (nuevaTarea.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresar la tarea modificada");
        } else {
            Main con = new Main();
            Connection conexion = con.establecerConeccion();

            if (conexion != null) {
                try {
                    //Obtener el Id de la tarea seleccionada en la tabla
                    int idSeleccionado = getIdSelected();

                    if (idSeleccionado != -1) {
                        String sql = "UPDATE tarea SET description = ?, state = ? WHERE id = ?";

                    PreparedStatement preparedStatement = conexion.prepareStatement(sql);

                    preparedStatement.setString(1, nuevaTarea);
                    preparedStatement.setString(2, nuevoEstado);
                    preparedStatement.setInt(3, idSeleccionado);
                       
                        

                        //Ejecutar la actualización
                        int filasAfectadas = preparedStatement.executeUpdate();

                        if (filasAfectadas > 0) {
                            //La actualización fue exitosa
                            JOptionPane.showMessageDialog(null, "Tarea  modificado exitosamente");
                        } else {
                            //Caso contrario que salga algo mal
                            JOptionPane.showMessageDialog(null, "No se pudo modificar la tarea");
                        }
                        //Cerrar la sentencia preparada
                        preparedStatement.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar la tarea");
                    }
                    //Cerrar la conexion
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar la tarea");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexion a la base de datos");
            }
        }
    };
    
    public void deleteTask(){
      int filaSeleccionada = tblTasks.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int idEliminar = (int) tblTasks.getValueAt(filaSeleccionada, 0);
            String sql = "DELETE FROM tarea WHERE id = " + idEliminar;

            try {
                Main con = new Main();

                Connection conexion = con.establecerConeccion();
                Statement st = conexion.createStatement();

                int filasAfectadas = st.executeUpdate(sql);

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "La tarea fué eliminada con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la tarea");
                }
                st.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    
    public void hecho(){
        //Obtener el nuevo nombnre de la tarea desde un campo de texto
        
        
        String nuevoEstado = txtEstado.getText();
        //Verificar si se proporcionó un nuevo nombre
        if (nuevoEstado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresar el estado");
        } else {
            Main con = new Main();
            Connection conexion = con.establecerConeccion();

            if (conexion != null) {
                try {
                    //Obtener el Id de la tarea seleccionada en la tabla
                    int idSeleccionado = getIdSelected();

                    if (idSeleccionado != -1) {
                        String sql = "UPDATE tarea SET state = ? WHERE id = ?";

                    PreparedStatement preparedStatement = conexion.prepareStatement(sql);

                    
                    preparedStatement.setString(1, nuevoEstado);
                    preparedStatement.setInt(2, idSeleccionado);
                       
                        //Ejecutar la actualización
                        int filasAfectadas = preparedStatement.executeUpdate();

                        if (filasAfectadas > 0) {
                            //La actualización fue exitosa
                            JOptionPane.showMessageDialog(null, "Estado modificado");
                        } else {
                            //Caso contrario que salga mal
                            JOptionPane.showMessageDialog(null, "No se pudo modificar el estado");
                        }
                        //Cerrar la sentencia preparada
                        preparedStatement.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el estado");
                    }
                    //Cerrar la conexion
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar el estado de la tarea seleccionada");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexion a la base de datos");
            }
        }
    
    }
    
/***************************************************************************************/    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtTarea = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        btnDone = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTasks = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnList = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTextField4.setText("jTextField4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 255));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("ID");

        jLabel2.setText("TAREA");

        jLabel3.setText("ESTADO");

        txtId.setEditable(false);
        txtId.setBackground(new java.awt.Color(204, 204, 204));
        txtId.setEnabled(false);

        txtTarea.setBackground(new java.awt.Color(204, 204, 204));

        txtEstado.setBackground(new java.awt.Color(204, 204, 204));

        btnDone.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnDone.setText("CHANGE STATE");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDone)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblTasks.setBackground(new java.awt.Color(102, 102, 102));
        tblTasks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblTasks.setForeground(new java.awt.Color(204, 204, 204));
        tblTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TAREA", "ESTADO"
            }
        ));
        tblTasks.setColumnSelectionAllowed(true);
        tblTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTasksMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTasks);
        tblTasks.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblTasks.getColumnModel().getColumnCount() > 0) {
            tblTasks.getColumnModel().getColumn(0).setMinWidth(30);
            tblTasks.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblTasks.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd.setBackground(new java.awt.Color(153, 153, 153));
        btnAdd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAdd.setText("ADD TASK");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddMousePressed(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(153, 153, 153));
        btnEdit.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEdit.setText("EDIT TASK");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(153, 153, 153));
        btnDelete.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnDelete.setText("DELETE TASK");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDeleteMousePressed(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnList.setBackground(new java.awt.Color(153, 153, 153));
        btnList.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnList.setText("TO LIST");
        btnList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnList, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnList, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 51, 153));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("To-Do-List");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**********************************BOTONES**********************************************/
    private void btnListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListActionPerformed
       
        toList();//se llama al metodo que lista todas las tareas existentes
 
    }//GEN-LAST:event_btnListActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String tarea = txtTarea.getText();
        String estado = txtEstado.getText();
        addTask(tarea,estado);
        toList();//se llama al metodo que lista todas las tareas existentes
        nuevo();        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
       edit();
       toList();//se llama al metodo que lista todas las tareas existentes
       nuevo();
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblTasksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTasksMouseClicked
        int fila = tblTasks.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Tarea no seleccionada");
        } else {
            int id = Integer.parseInt((String) tblTasks.getValueAt(fila, 0).toString());
            String t = (String) tblTasks.getValueAt(fila, 1);
            String est = (String) tblTasks.getValueAt(fila, 2);
            txtId.setText("" + id);
            txtTarea.setText(t);
            txtEstado.setText(est);
        }
    }//GEN-LAST:event_tblTasksMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       deleteTask();//se llama al metodo que borra la tarea seleccionada
       toList();//se llama al metodo que lista todas las tareas existentes
       nuevo();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        hecho();
        toList();
        nuevo();
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed

        // Cerrar la aplicación
        System.exit(0);
       
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        
    }//GEN-LAST:event_btnAddMousePressed

    private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMousePressed
        
    }//GEN-LAST:event_btnDeleteMousePressed

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        
    }//GEN-LAST:event_btnDeleteMouseClicked

    /**
     * @param args the command line arguments
     */ // Variables declaration - do not modify                     
    // End of variables declaration    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable tblTasks;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTarea;
    // End of variables declaration//GEN-END:variables
}
