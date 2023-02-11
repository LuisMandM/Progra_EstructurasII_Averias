package com.ikasgela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Tecnico> tecnicos_Alta = new ArrayList<>();
    public static List<Averia> averias_Alta = new ArrayList<>();
    public static List<Averia> averias_No_Asignadas = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean runnig = true;
        do {
            System.out.print("\n---Menu Tecnicos-Averias---\n1. Alta de técnico.\n2. Gestión de averías" +
                    "\n3. Listados\n4. Salir\nOpcion: ");
            try {
                int opcion_Init = Integer.parseInt(br.readLine());
                switch (opcion_Init) {
                    case 1:

                        AltaTecnico(br);

                        break;
                    case 2:
                        System.out.print("\na. Nuevo parte de avería.\n" +
                                "b. Asignar avería a técnico.\n" +
                                "c. Atender avería.\n(otra).Volver\nOpcion: ");
                        char opcion_Gest = br.readLine().toLowerCase().charAt(0);
                        switch (opcion_Gest) {
                            case 'a':
                                AltaAveria(br);
                                break;
                            case 'b':

                                AsignacionAveria(br);
                                break;
                            case 'c':
                                TratarAveria(br);
                                break;

                            default:
                                break;
                        }

                        break;
                    case 3:
                        System.out.print("\na. Listado de averías sin resolver de un técnico.\n" +
                                "b. Listado de todas las averías de un técnico (resueltas y no resueltas).\n" +
                                "c. Listado de todas las averías del sistema y técnico asignado." +
                                "\n(otra).Volver\nOpcion: ");
                        char opcion_List = br.readLine().toLowerCase().charAt(0);
                        switch (opcion_List) {
                            case 'a':

                                if (tecnicos_Alta.size() > 0) {
                                    ListarPendientes(br);
                                } else {
                                    System.out.println("No hay Tecnicos dados de alta aún");
                                }
                                break;
                            case 'b':
                                if (tecnicos_Alta.size() > 0) {
                                    ListarHistorialAverias(br);
                                } else {
                                    System.out.println("No hay Tecnicos dados de alta aún");
                                }
                                break;
                            case 'c':

                                if (averias_Alta.size() > 0) {
                                    for (Averia averia : averias_Alta) {
                                        if (averia.getTecnico_Asignado() != null) {
                                            System.out.println(averia + "\nTecnico: "
                                                    + averia.getTecnico_Asignado().getNombre() + "\n");
                                        } else {
                                            System.out.println(averia + "\nTecnico: No asignado\n");
                                        }
                                    }
                                } else {
                                    System.out.println("No hay averias aun ingresadas;");
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4:
                        runnig = false;
                        System.out.println("Saliendo....");
                        break;
                    default:
                        System.out.println("Opcion Invalida\n");
                        break;

                }
            } catch (NumberFormatException e) {
                System.out.println("EROR: Tipo de dato ingresado invalido");
            }

        } while (runnig);


    }

    private static void TratarAveria(BufferedReader br) throws IOException {
        System.out.println("Indique el tecnico al que desea gestionar las Averias: ");
        VisualizarTecnicos();
        System.out.print("Opcion: ");
        try {
            int tec_elec = Integer.parseInt(br.readLine()) - 1;

            if (tec_elec >= tecnicos_Alta.size() || tec_elec < 0) {
                System.out.println("Error: Opción fuera de los limites");
                return;
            }

            Tecnico tec_InGest = tecnicos_Alta.get(tec_elec);

            if (tec_InGest.getAverias_Pendientes().size() > 0) {
                for (int i = 0; i < tec_InGest.getAverias_Pendientes().size(); i++) {
                    System.out.println((i + 1) + "-" + tec_InGest.getAverias_Pendientes().get(i).getTitulo() + "-Lvl: "
                            + tec_InGest.getAverias_Pendientes().get(i).getNivel() + "\n");
                }
                System.out.print("Opcion: ");
                int aver_elec = Integer.parseInt(br.readLine()) - 1;

                if (aver_elec >= tec_InGest.getAverias_Pendientes().size() || aver_elec < 0) {
                    System.out.println("Error: Opción fuera de los limites");
                    return;
                }

                Averia aver_InGest = tec_InGest.averias_Pendientes.get(aver_elec);
                tec_InGest.getAverias_Pendientes().remove(aver_InGest);

                if (aver_InGest.getNivel() <= tec_InGest.getNivel()) {
                    System.out.println("Averia " + aver_InGest.getTitulo() + " Resuelta");
                } else {
                    if (aver_InGest.getNivel() > 1) {
                        aver_InGest.setNivel(aver_InGest.getNivel() - 1);
                    }
                    tec_InGest.getAverias_Pendientes().addLast(aver_InGest);
                    System.out.println("Averia " + aver_InGest.getTitulo() + " NO Resuelta");
                }
            } else {
                System.out.println("Sin Averias para tratar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("EROR: Tipo de dato ingresado invalido");
        }
    }

    private static void AsignacionAveria(BufferedReader br) throws IOException {
        if (tecnicos_Alta.size() > 0) {
            if (averias_No_Asignadas.size() > 0) {
                System.out.println("Indique la Averia que desea Asignar");
                VisualizarAveriasNoGest();
                System.out.print("Opcion: ");
                try {
                    int averia_elec = Integer.parseInt(br.readLine()) - 1;

                    if (averia_elec >= averias_No_Asignadas.size() || averia_elec < 0) {
                        System.out.println("Error: Opción fuera de los limites");
                        return;
                    }

                    System.out.println("Indique el tecnico al que desea Asignar la Averia: ");
                    VisualizarTecnicos();
                    System.out.print("Opcion: ");
                    int tec_elec = Integer.parseInt(br.readLine()) - 1;

                    if (tec_elec >= tecnicos_Alta.size() || tec_elec < 0) {
                        System.out.println("Error: Opción fuera de los limites");
                        return;
                    }

                    tecnicos_Alta.get(tec_elec).add_Averia(averias_No_Asignadas.get(averia_elec));
                    averias_No_Asignadas.get(averia_elec).setTecnico_Asignado(tecnicos_Alta.get(tec_elec));
                    averias_No_Asignadas.remove(averia_elec);

                    System.out.println("Averia asignada correctamente");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Tipo de dato ingresado invalido");
                }
            } else {
                System.out.println("Sin Averias Reportadas");
            }
        } else {
            System.out.println("Sin Tecnicos dados de Alta");
        }
    }

    private static void AltaAveria(BufferedReader br) throws IOException {
        System.out.print("\nIndique el nombre de la averia: ");
        String nombre_Fail = br.readLine();
        System.out.print("Describa la averia: ");
        String desc_Fail = br.readLine();
        try {
            LocalDateTime date_Fail;
            boolean date_Ok = false;
            do {
                System.out.println("A continuacion se pediran los datos de fecha y hora de la averia:");
                date_Fail = CreateDate(br);
                if (date_Fail != null) {
                    date_Ok = true;
                }
            } while (!date_Ok);

            int nivel_Fail;
            boolean nivel_Ok = false;
            do {
                System.out.print("Indique el nivel del tecnico de 1-3, ambos incluidos: ");
                nivel_Fail = Integer.parseInt(br.readLine());
                if (nivel_Fail >= 1 && nivel_Fail <= 3) {
                    nivel_Ok = true;
                } else {
                    System.out.println("Nivel Incorrecto intente nuevamente");
                }
            } while (!nivel_Ok);
            Averia actual_Fail = new Averia(nombre_Fail, desc_Fail, date_Fail, nivel_Fail);
            averias_Alta.add(actual_Fail);
            averias_No_Asignadas.add(actual_Fail);
            System.out.println("Averia Creada Exitosamente");
        } catch (DateTimeException e) {
            System.out.println("Error: Fecha invalida\n");
        } catch (NumberFormatException e) {
            System.out.println("Error: Tipo de dato ingresado invalido");
        }
    }

    private static void AltaTecnico(BufferedReader br) throws IOException {
        System.out.print("\nIndique el nombre del Tecnico: ");
        String nombre_Tec = br.readLine();

        try {
            int nivel_Tec;
            boolean nivel_Ok = false;
            do {
                System.out.print("Indique el nivel del tecnico de 1-3, ambos incluidos: ");
                nivel_Tec = Integer.parseInt(br.readLine());
                if (nivel_Tec >= 1 && nivel_Tec <= 3) {
                    nivel_Ok = true;
                } else {
                    System.out.println("Nivel Incorrecto intente nuevamente");
                }

            } while (!nivel_Ok);

            Tecnico tec_Actual = new Tecnico(nombre_Tec, nivel_Tec);
            tecnicos_Alta.add(tec_Actual);
            System.out.println("Tecnico creado Exitosamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: Tipo de dato ingresado invalido");
        }
    }

    private static void ListarHistorialAverias(BufferedReader br) throws IOException {
        System.out.println("Indique el tecnico del que desea revisar las Averias: ");
        VisualizarTecnicos();
        System.out.print("Opcion: ");
        try {
            int tec_elec = Integer.parseInt(br.readLine()) - 1;

            if (tec_elec >= tecnicos_Alta.size() || tec_elec < 0) {
                System.out.println("Error: Opción fuera de los limites");
                return;
            }

            if (tecnicos_Alta.get(tec_elec).getHistorial_Averias().size() > 0) {
                for (int i = 0; i < tecnicos_Alta.get(tec_elec).getHistorial_Averias().size(); i++) {


                    if (tecnicos_Alta.get(tec_elec).getAverias_Pendientes().contains(tecnicos_Alta.get(tec_elec).getHistorial_Averias().get(i))) {
                        System.out.println("Averia " + (i + 1) + ":\n" +
                                tecnicos_Alta.get(tec_elec).getHistorial_Averias().get(i) + "Estado: Pendiente\n");
                    } else {
                        System.out.println("Averia " + (i + 1) + ":\n" +
                                tecnicos_Alta.get(tec_elec).getHistorial_Averias().get(i) + "Estado: Resuelto\n");
                    }

                }
            } else {
                System.out.println("Tecnico sin averias asignadas aun.");
            }
        } catch (NumberFormatException e) {
            System.out.println("EROR: Tipo de dato ingresado invalido");
        }
    }

    private static void ListarPendientes(BufferedReader br) throws IOException {
        System.out.println("Indique el tecnico del que desea revisar las Averias: ");
        VisualizarTecnicos();
        System.out.print("Opcion: ");
        try {
            int tec_elec = Integer.parseInt(br.readLine()) - 1;

            if (tec_elec >= tecnicos_Alta.size() || tec_elec < 0) {
                System.out.println("Error: Opción fuera de los limites");
                return;
            }


            if (tecnicos_Alta.get(tec_elec).getAverias_Pendientes().size() > 0) {
                for (int i = 0; i < tecnicos_Alta.get(tec_elec).getAverias_Pendientes().size(); i++) {
                    System.out.println("Averia " + (i + 1) + ":\n" +
                            tecnicos_Alta.get(tec_elec).getAverias_Pendientes().get(i) + "\n");
                }
            } else {
                System.out.println("Tecnico sin averias pendientes");
            }
        } catch (NumberFormatException e) {
            System.out.println("EROR: Tipo de dato ingresado invalido");
        }
    }

    private static void VisualizarAveriasNoGest() {
        if (averias_No_Asignadas.size() > 0) {
            System.out.println("Averias NO Gestionadas:");
            for (int i = 0; i < averias_No_Asignadas.size(); i++) {
                System.out.println((i + 1) + "-" + averias_No_Asignadas.get(i).getTitulo());
            }
        } else {
            System.out.println("Sin Averias pendientes de asignación.");
        }
        System.out.println();
    }

    private static void VisualizarTecnicos() {
        if (tecnicos_Alta.size() > 0) {
            System.out.println("Tecnicos creados:");
            for (int i = 0; i < tecnicos_Alta.size(); i++) {
                System.out.println((i + 1) + "-" + tecnicos_Alta.get(i).getNombre());
            }
        } else {
            System.out.println("Sin Tecnicos Creados Aun.");
        }
        System.out.println();
    }


    private static LocalDateTime CreateDate(BufferedReader br) throws IOException {
        System.out.print("Ingrese el dia, formato DD: ");
        int dia = Integer.parseInt(br.readLine());
        if (dia <= 0 || dia > 31) {
            System.out.println("Error: dato de DIA fuera de los valores validos\n");
            return null;
        }
        System.out.print("Ingrese el mes, formato MM: ");
        int mes = Integer.parseInt(br.readLine());
        if (mes <= 0 || mes > 12) {
            System.out.println("Error: dato de MES fuera de los valores validos\n");
            return null;
        }
        System.out.print("Ingrese el año, formato YYYY: ");
        int anio = Integer.parseInt(br.readLine());
        if (anio <= 0) {
            System.out.println("Error: dato de AÑO fuera de los valores validos\n");
            return null;
        }
        System.out.print("Ingrese la hora, formato HH: ");
        int hora = Integer.parseInt(br.readLine());
        if (hora < 0 || hora > 24) {
            System.out.println("Error: dato de HORA fuera de los valores validos\n");
            return null;
        }
        System.out.print("Ingrese los minutos, formato MM: ");
        int min = Integer.parseInt(br.readLine());
        if (min < 0 || min > 59) {
            System.out.println("Error: dato de MINUTOS fuera de los valores validos\n");
            return null;
        }
        return LocalDateTime.of(anio, mes, dia, hora, min);
    }
}
