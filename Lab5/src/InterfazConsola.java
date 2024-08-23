import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.AttributeSet;

public class InterfazConsola extends JFrame {
    private JTextArea salidaConsola;
    private ProcesadorComandos procesadorComandos;
    private String prompt;
    private int posicionUltimoComando;

    public InterfazConsola() {
        procesadorComandos = new ProcesadorComandos();

        setTitle("ADMINISTRADOR: COMMAND PROMPT");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        salidaConsola = new JTextArea();
        salidaConsola.setEditable(true);
        salidaConsola.setBackground(Color.BLACK);
        salidaConsola.setForeground(Color.WHITE);
        salidaConsola.setFont(new Font("Monospaced", Font.PLAIN, 14));
        salidaConsola.append("Último inicio de sesión: Lun Ago 19 12:07:49 en ttys000\n");

        prompt = procesadorComandos.obtenerDirectorioActual() + " % ";
        salidaConsola.append(prompt);
        posicionUltimoComando = salidaConsola.getDocument().getLength();

        JScrollPane scrollPane = new JScrollPane(salidaConsola);
        add(scrollPane, BorderLayout.CENTER);

        PlainDocument doc = (PlainDocument) salidaConsola.getDocument();
        doc.setDocumentFilter(new FiltroDocumentoNoEditable());

        salidaConsola.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    manejarPresionEnter();
                } else if (salidaConsola.getCaretPosition() < posicionUltimoComando) {
                    salidaConsola.setCaretPosition(salidaConsola.getDocument().getLength());
                }
            }

            public void keyPressed(KeyEvent e) {
                if (salidaConsola.getCaretPosition() < posicionUltimoComando) {
                    salidaConsola.setCaretPosition(salidaConsola.getDocument().getLength());
                }
                manejarTeclasFlecha(e);
            }
        });

        salidaConsola.setCaretColor(Color.WHITE);
        salidaConsola.setCaretPosition(salidaConsola.getDocument().getLength());
    }

    private void manejarPresionEnter() {
        try {
            int end = salidaConsola.getDocument().getLength();
            String comando = salidaConsola.getText(posicionUltimoComando, end - posicionUltimoComando).trim();
            String salida = procesadorComandos.procesarComando(comando);

            salidaConsola.append("\n" + salida + "\n");
            prompt = procesadorComandos.obtenerDirectorioActual() + " % ";
            salidaConsola.append(prompt);
            posicionUltimoComando = salidaConsola.getDocument().getLength();
            salidaConsola.setCaretPosition(posicionUltimoComando);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void manejarTeclasFlecha(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            String salida = procesadorComandos.procesarComando("..");
            salidaConsola.append("\n" + salida + "\n");
            prompt = procesadorComandos.obtenerDirectorioActual() + " % ";
            salidaConsola.append(prompt);
            posicionUltimoComando = salidaConsola.getDocument().getLength();
            salidaConsola.setCaretPosition(posicionUltimoComando);
        }
    }

    private class FiltroDocumentoNoEditable extends DocumentFilter {
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset >= posicionUltimoComando) {
                super.remove(fb, offset, length);
            }
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (offset >= posicionUltimoComando) {
                super.insertString(fb, offset, string, attr);
            }
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (offset >= posicionUltimoComando) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
