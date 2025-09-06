/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class FrmComparador extends javax.swing.JFrame {
    private ArrayList<Integer> listaNumeros = new ArrayList<>();
    private int pasosMezcla = 0;
    private int pasosFusion = 0;
    
    private void mostrarResultado(String algoritmo, long tiempo, int pasos, ArrayList<Integer> resultado) {
        txtResultados.append("\n\nAlgoritmo: " + algoritmo + "\n");
        txtResultados.append("Tiempo de ejecución: " + tiempo + " ms\n");
        txtResultados.append("Pasos realizados: " + pasos + "\n");
        txtResultados.append("Resultado ordenado (primeros 500 números): \n");
        int limite = Math.min(500, resultado.size());
        txtResultados.append(resultado.subList(0, limite).toString() + "\n");
    }
    
    private ArrayList<Integer> mezclaDirecta(ArrayList<Integer> lista) {
        pasosMezcla++; 
        if (lista.size() <= 1) {
            return lista;
        }

        int medio = lista.size() / 2;

        ArrayList<Integer> izquierda = new ArrayList<>(lista.subList(0, medio));
        ArrayList<Integer> derecha = new ArrayList<>(lista.subList(medio, lista.size()));

        izquierda = mezclaDirecta(izquierda);
        derecha = mezclaDirecta(derecha);

        return fusionarMezcla(izquierda, derecha);
    }

    private ArrayList<Integer> fusionarMezcla(ArrayList<Integer> izquierda, ArrayList<Integer> derecha) {
        ArrayList<Integer> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            pasosMezcla++;
            if (izquierda.get(i) <= derecha.get(j)) {
                resultado.add(izquierda.get(i));
                i++;
            } else {
                resultado.add(derecha.get(j));
                j++;
            }
        }

        while (i < izquierda.size()) {
            resultado.add(izquierda.get(i));
            i++;
        }
        while (j < derecha.size()) {
            resultado.add(derecha.get(j));
            j++;
        }

        return resultado;
    }
    
    private ArrayList<Integer> fusionNatural(ArrayList<Integer> lista) {
        pasosFusion = 0;
        ArrayList<ArrayList<Integer>> runs = new ArrayList<>();
        ArrayList<Integer> actual = new ArrayList<>();
        actual.add(lista.get(0));

        for (int i = 1; i < lista.size(); i++) {
            pasosFusion++;
            if (lista.get(i) >= lista.get(i - 1)) {
                actual.add(lista.get(i));
            } else {
                runs.add(new ArrayList<>(actual));
                actual.clear();
                actual.add(lista.get(i));
            }
        }
        runs.add(actual);

        while (runs.size() > 1) {
            ArrayList<Integer> izquierda = runs.remove(0);
            ArrayList<Integer> derecha = runs.remove(0);
            ArrayList<Integer> fusion = fusionarFusion(izquierda, derecha);
            runs.add(fusion);
        }

        return runs.get(0);
    }

    private ArrayList<Integer> fusionarFusion(ArrayList<Integer> izquierda, ArrayList<Integer> derecha) {
        ArrayList<Integer> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            pasosFusion++;
            if (izquierda.get(i) <= derecha.get(j)) {
                resultado.add(izquierda.get(i));
                i++;
            } else {
                resultado.add(derecha.get(j));
                j++;
            }
        }

        while (i < izquierda.size()) {
            resultado.add(izquierda.get(i));
            i++;
        }
        while (j < derecha.size()) {
            resultado.add(derecha.get(j));
            j++;
        }

        return resultado;
    }
    
    private ArrayList<Integer> mezclaEquilibradaMultiple(ArrayList<Integer> lista) {
        int pasos = 0;
        txtResultados.append("\nIniciando Mezcla Equilibrada Múltiple...\n");

        int partes = 3; 
        ArrayList<ArrayList<Integer>> segmentos = new ArrayList<>();

        int tam = lista.size() / partes;
        for (int i = 0; i < lista.size(); i += tam) {
            int fin = Math.min(i + tam, lista.size());
            ArrayList<Integer> sublista = new ArrayList<>(lista.subList(i, fin));
            Collections.sort(sublista);
            segmentos.add(sublista);
            txtResultados.append("Segmento ordenado: " + sublista + "\n");
            pasos++;
        }

        ArrayList<Integer> resultado = new ArrayList<>();
        for (ArrayList<Integer> seg : segmentos) {
            resultado.addAll(seg);
            pasos++;
        }
        Collections.sort(resultado);

        txtResultados.append("Resultado final: " + resultado + "\n");
        txtResultados.append("Pasos realizados: " + pasos + "\n");
        return resultado;
    }
    
    private ArrayList<Integer> metodoPolifasico(ArrayList<Integer> lista) {
        int pasos = 0;
        txtResultados.append("\nIniciando Método Polifásico...\n");

        ArrayList<ArrayList<Integer>> runs = new ArrayList<>();

        int i = 0;
        while (i < lista.size()) {
            int tamCorrida = Math.min((i % 5) + 3, lista.size() - i); 
            ArrayList<Integer> corrida = new ArrayList<>(lista.subList(i, i + tamCorrida));
            Collections.sort(corrida);
            runs.add(corrida);
            txtResultados.append("Corrida creada: " + corrida + "\n");
            pasos++;
            i += tamCorrida;
        }

        while (runs.size() > 1) {
            ArrayList<Integer> a = runs.remove(0);
            ArrayList<Integer> b = runs.remove(0);
            ArrayList<Integer> fusion = fusionarMezcla(a, b); 
            txtResultados.append("Fusionando: " + a + " + " + b + " => " + fusion + "\n");
            runs.add(fusion);
            pasos++;
        }

        txtResultados.append("Resultado final: " + runs.get(0) + "\n");
        txtResultados.append("Pasos realizados: " + pasos + "\n");
        return runs.get(0);
    }

    /**
     * Creates new form FrmComparador
     */
    public FrmComparador() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCargar = new javax.swing.JButton();
        btnMezcla = new javax.swing.JButton();
        btnFusion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultados = new javax.swing.JTextArea();
        btnEquilibrada = new javax.swing.JButton();
        btnPolifasico = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCargar.setText("Cargar Números Aleatorios");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        btnMezcla.setText("Mezcla Directa");
        btnMezcla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMezclaActionPerformed(evt);
            }
        });

        btnFusion.setText("Fusión Natural");
        btnFusion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFusionActionPerformed(evt);
            }
        });

        jLabel1.setText("Resultados de la Comparación");

        txtResultados.setEditable(false);
        txtResultados.setColumns(20);
        txtResultados.setRows(5);
        jScrollPane1.setViewportView(txtResultados);

        btnEquilibrada.setText("Mezcla Equilibrada Múltiple");
        btnEquilibrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquilibradaActionPerformed(evt);
            }
        });

        btnPolifasico.setText("Método Polifásico");
        btnPolifasico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPolifasicoActionPerformed(evt);
            }
        });

        jLabel2.setText("Ordenar con:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(56, 56, 56))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(btnEquilibrada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(btnPolifasico))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnMezcla)
                                .addGap(120, 120, 120)
                                .addComponent(btnFusion)
                                .addGap(8, 8, 8)))
                        .addGap(140, 140, 140))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(233, 233, 233))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(btnCargar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnCargar)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFusion)
                    .addComponent(btnMezcla))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEquilibrada)
                    .addComponent(btnPolifasico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        // TODO add your handling code here:
        listaNumeros.clear(); 
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            listaNumeros.add(random.nextInt(10000) + 1);
        }

        txtResultados.setText("Se han generado 1000 números aleatorios:\n");
        txtResultados.append(listaNumeros.toString());
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnMezclaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMezclaActionPerformed
        // TODO add your handling code here:
        if (listaNumeros.isEmpty()) {
            txtResultados.setText("Primero cargue los números aleatorios.\n");
            return;
        }

        pasosMezcla = 0; // reiniciar contador
        long inicio = System.currentTimeMillis();
        ArrayList<Integer> resultado = mezclaDirecta(new ArrayList<>(listaNumeros));
        long fin = System.currentTimeMillis();

        mostrarResultado("Mezcla Directa", (fin - inicio), pasosMezcla, resultado);
    }//GEN-LAST:event_btnMezclaActionPerformed

    private void btnFusionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFusionActionPerformed
        // TODO add your handling code here:
        if (listaNumeros.isEmpty()) {
            txtResultados.setText("Primero cargue los números aleatorios.\n");
            return;
        }

        pasosFusion = 0; // reiniciar contador
        long inicio = System.currentTimeMillis();
        ArrayList<Integer> resultado = fusionNatural(new ArrayList<>(listaNumeros));
        long fin = System.currentTimeMillis();

        mostrarResultado("Fusión Natural", (fin - inicio), pasosFusion, resultado);
    }//GEN-LAST:event_btnFusionActionPerformed

    private void btnEquilibradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquilibradaActionPerformed
        // TODO add your handling code here                                           
            if (listaNumeros.isEmpty()) {
                txtResultados.setText("Primero cargue los números aleatorios.\n");
                return;
            }

            long inicio = System.currentTimeMillis();
            ArrayList<Integer> resultado = mezclaEquilibradaMultiple(new ArrayList<>(listaNumeros));
            long fin = System.currentTimeMillis();

            txtResultados.append("Tiempo de ejecución: " + (fin - inicio) + " ms\n");
    }//GEN-LAST:event_btnEquilibradaActionPerformed

    private void btnPolifasicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPolifasicoActionPerformed
        // TODO add your handling code here:
        if (listaNumeros.isEmpty()) {
            txtResultados.setText("Primero cargue los números aleatorios.\n");
            return;
        }

        long inicio = System.currentTimeMillis();
        ArrayList<Integer> resultado = metodoPolifasico(new ArrayList<>(listaNumeros));
        long fin = System.currentTimeMillis();

        txtResultados.append("Tiempo de ejecución: " + (fin - inicio) + " ms\n");
    }//GEN-LAST:event_btnPolifasicoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmComparador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmComparador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmComparador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmComparador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmComparador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnEquilibrada;
    private javax.swing.JButton btnFusion;
    private javax.swing.JButton btnMezcla;
    private javax.swing.JButton btnPolifasico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtResultados;
    // End of variables declaration//GEN-END:variables
}
