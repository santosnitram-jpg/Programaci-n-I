import java.util.Scanner;

/*
 * ============================================================
 *  SISTEMA DE CONTROL DE GASTOS PERSONALES
 *  Proyecto Integrador - Programacion I
 *  Sprint 1
 * ------------------------------------------------------------
 *  Evidencia: variables, tipos de datos, operadores, secuencia,
 *  if, if-else, switch, for, while, do-while, contador y acumulador.
 * ============================================================
 */
public class SistemaGastos {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ---------- VARIABLES Y TIPOS DE DATOS ----------
        final int MAX = 100;                       // capacidad maxima de gastos
        String nombreUsuario = "";                 // cadena
        double presupuestoMensual = 0;             // decimal
        boolean usuarioRegistrado = false;         // logico

        String[] descripcion = new String[MAX];    // arreglo de cadenas
        String[] categoria = new String[MAX];      // arreglo de cadenas
        String[] fecha = new String[MAX];          // arreglo de cadenas
        double[] monto = new double[MAX];          // arreglo de decimales

        int cantidadGastos = 0;                    // CONTADOR de gastos
        double totalGastado = 0;                   // ACUMULADOR de montos

        int opcion;                                // entero (opcion del menu)

        // ---------- MENU PRINCIPAL (do-while + switch) ----------
        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println(" SISTEMA DE CONTROL DE GASTOS");
            System.out.println("=================================");
            System.out.println("1. Registrar usuario y presupuesto");
            System.out.println("2. Registrar gasto");
            System.out.println("3. Mostrar gastos");
            System.out.println("4. Calcular total gastado");
            System.out.println("5. Buscar gastos por categoria");
            System.out.println("6. Ordenar gastos por monto");
            System.out.println("7. Mostrar gasto mayor y menor");
            System.out.println("8. Salir");
            System.out.println("=================================");
            System.out.print("Seleccione una opcion: ");

            // VALIDACION con while: solo acepta numeros enteros
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.print("Entrada invalida. Ingrese un numero: ");
            }
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                // ================= OPCION 1: REGISTRAR USUARIO =================
                case 1:
                    System.out.print("Nombre del usuario: ");
                    nombreUsuario = sc.nextLine();

                    // do-while: el presupuesto debe ser mayor a cero
                    do {
                        System.out.print("Presupuesto mensual (Bs): ");
                        while (!sc.hasNextDouble()) {
                            sc.next();
                            System.out.print("Monto invalido. Ingrese un numero: ");
                        }
                        presupuestoMensual = sc.nextDouble();
                        sc.nextLine();

                        if (presupuestoMensual <= 0) {
                            System.out.println("El presupuesto debe ser mayor a 0.");
                        }
                    } while (presupuestoMensual <= 0);

                    usuarioRegistrado = true;
                    System.out.println("Usuario registrado correctamente.");
                    break;

                // ================= OPCION 2: REGISTRAR GASTO =================
                case 2:
                    if (!usuarioRegistrado) {
                        System.out.println("Primero debe registrar al usuario (opcion 1).");
                    } else if (cantidadGastos >= MAX) {
                        System.out.println("Se alcanzo el limite de " + MAX + " gastos.");
                    } else {
                        System.out.print("Descripcion del gasto: ");
                        descripcion[cantidadGastos] = sc.nextLine();

                        // Submenu de categorias (switch)
                        System.out.println("Categorias disponibles:");
                        System.out.println("  1. Alimentacion   2. Transporte     3. Vivienda");
                        System.out.println("  4. Educacion      5. Salud          6. Entretenimiento");
                        System.out.println("  7. Otros");
                        System.out.print("Seleccione la categoria: ");

                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Entrada invalida. Ingrese un numero: ");
                        }
                        int opcionCategoria = sc.nextInt();
                        sc.nextLine();

                        switch (opcionCategoria) {
                            case 1:
                                categoria[cantidadGastos] = "Alimentacion";
                                break;
                            case 2:
                                categoria[cantidadGastos] = "Transporte";
                                break;
                            case 3:
                                categoria[cantidadGastos] = "Vivienda";
                                break;
                            case 4:
                                categoria[cantidadGastos] = "Educacion";
                                break;
                            case 5:
                                categoria[cantidadGastos] = "Salud";
                                break;
                            case 6:
                                categoria[cantidadGastos] = "Entretenimiento";
                                break;
                            case 7:
                                categoria[cantidadGastos] = "Otros";
                                break;
                            default:
                                categoria[cantidadGastos] = "Otros";
                                System.out.println("Opcion no valida, se asigno la categoria Otros.");
                                break;
                        }

                        // do-while: el monto debe ser mayor a cero
                        double montoNuevo;
                        do {
                            System.out.print("Monto del gasto (Bs): ");
                            while (!sc.hasNextDouble()) {
                                sc.next();
                                System.out.print("Monto invalido. Ingrese un numero: ");
                            }
                            montoNuevo = sc.nextDouble();
                            sc.nextLine();

                            if (montoNuevo <= 0) {
                                System.out.println("El monto debe ser mayor a 0.");
                            }
                        } while (montoNuevo <= 0);

                        monto[cantidadGastos] = montoNuevo;

                        System.out.print("Fecha (dd/mm/aaaa): ");
                        fecha[cantidadGastos] = sc.nextLine();

                        totalGastado = totalGastado + montoNuevo;  // ACUMULADOR
                        cantidadGastos = cantidadGastos + 1;       // CONTADOR

                        System.out.println("Gasto registrado. Gastos acumulados: " + cantidadGastos);

                        // if-else: alerta de presupuesto (operadores relacionales)
                        if (totalGastado > presupuestoMensual) {
                            System.out.println("ALERTA: supero su presupuesto en Bs "
                                    + (totalGastado - presupuestoMensual));
                        } else {
                            System.out.println("Saldo disponible: Bs "
                                    + (presupuestoMensual - totalGastado));
                        }
                    }
                    break;

                // ================= OPCION 3: MOSTRAR GASTOS =================
                case 3:
                    if (cantidadGastos == 0) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        System.out.println();
                        System.out.println("LISTADO DE GASTOS DE " + nombreUsuario.toUpperCase());
                        System.out.println("N  DESCRIPCION          CATEGORIA         FECHA        MONTO");
                        System.out.println("------------------------------------------------------------");

                        for (int i = 0; i < cantidadGastos; i++) {   // for
                            System.out.println((i + 1) + "  " + descripcion[i] + "   "
                                    + categoria[i] + "   " + fecha[i] + "   Bs " + monto[i]);
                        }
                        System.out.println("------------------------------------------------------------");
                        System.out.println("Registros mostrados: " + cantidadGastos);
                    }
                    break;

                // ================= OPCION 4: CALCULAR TOTAL =================
                case 4:
                    if (cantidadGastos == 0) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        double suma = 0;        // ACUMULADOR
                        int contador = 0;       // CONTADOR
                        int i = 0;

                        while (i < cantidadGastos) {   // while
                            suma = suma + monto[i];
                            contador = contador + 1;
                            i = i + 1;
                        }

                        double promedio = suma / contador;   // operador aritmetico

                        System.out.println("Cantidad de gastos : " + contador);
                        System.out.println("Total gastado      : Bs " + suma);
                        System.out.println("Promedio por gasto : Bs " + promedio);
                        System.out.println("Presupuesto        : Bs " + presupuestoMensual);

                        // if / else if / else: nivel de consumo
                        double porcentaje = (suma * 100) / presupuestoMensual;
                        System.out.println("Consumo del presupuesto: " + porcentaje + " %");

                        if (porcentaje <= 50) {
                            System.out.println("Nivel de gasto: BAJO");
                        } else if (porcentaje <= 80) {
                            System.out.println("Nivel de gasto: MODERADO");
                        } else if (porcentaje <= 100) {
                            System.out.println("Nivel de gasto: ALTO");
                        } else {
                            System.out.println("Nivel de gasto: EXCEDIDO");
                        }
                    }
                    break;

                // ================= OPCION 5: BUSCAR POR CATEGORIA =================
                case 5:
                    if (cantidadGastos == 0) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        System.out.print("Ingrese la categoria a buscar: ");
                        String buscada = sc.nextLine();

                        int encontrados = 0;       // CONTADOR
                        double subtotal = 0;       // ACUMULADOR

                        for (int i = 0; i < cantidadGastos; i++) {
                            if (categoria[i].equalsIgnoreCase(buscada)) {
                                System.out.println("- " + descripcion[i] + " | " + fecha[i]
                                        + " | Bs " + monto[i]);
                                encontrados = encontrados + 1;
                                subtotal = subtotal + monto[i];
                            }
                        }

                        if (encontrados == 0) {
                            System.out.println("No se encontraron gastos en la categoria " + buscada + ".");
                        } else {
                            System.out.println("Gastos encontrados: " + encontrados);
                            System.out.println("Subtotal: Bs " + subtotal);
                        }
                    }
                    break;

                // ================= OPCION 6: ORDENAR POR MONTO =================
                case 6:
                    if (cantidadGastos == 0) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        System.out.print("Ordenar 1) Mayor a menor  2) Menor a mayor: ");
                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Entrada invalida. Ingrese 1 o 2: ");
                        }
                        int tipoOrden = sc.nextInt();
                        sc.nextLine();

                        // Metodo burbuja: for anidado
                        for (int i = 0; i < cantidadGastos - 1; i++) {
                            for (int j = 0; j < cantidadGastos - 1 - i; j++) {

                                boolean intercambiar = false;

                                if (tipoOrden == 2) {
                                    if (monto[j] > monto[j + 1]) {
                                        intercambiar = true;
                                    }
                                } else {
                                    if (monto[j] < monto[j + 1]) {
                                        intercambiar = true;
                                    }
                                }

                                if (intercambiar) {
                                    double auxMonto = monto[j];
                                    monto[j] = monto[j + 1];
                                    monto[j + 1] = auxMonto;

                                    String auxDesc = descripcion[j];
                                    descripcion[j] = descripcion[j + 1];
                                    descripcion[j + 1] = auxDesc;

                                    String auxCat = categoria[j];
                                    categoria[j] = categoria[j + 1];
                                    categoria[j + 1] = auxCat;

                                    String auxFecha = fecha[j];
                                    fecha[j] = fecha[j + 1];
                                    fecha[j + 1] = auxFecha;
                                }
                            }
                        }

                        if (tipoOrden == 2) {
                            System.out.println("Gastos ordenados de menor a mayor:");
                        } else {
                            System.out.println("Gastos ordenados de mayor a menor:");
                        }

                        for (int i = 0; i < cantidadGastos; i++) {
                            System.out.println((i + 1) + ". " + descripcion[i] + " ("
                                    + categoria[i] + ") Bs " + monto[i]);
                        }
                    }
                    break;

                // ================= OPCION 7: MAYOR Y MENOR =================
                case 7:
                    if (cantidadGastos == 0) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        int posMayor = 0;
                        int posMenor = 0;

                        for (int i = 1; i < cantidadGastos; i++) {
                            if (monto[i] > monto[posMayor]) {
                                posMayor = i;
                            }
                            if (monto[i] < monto[posMenor]) {
                                posMenor = i;
                            }
                        }

                        System.out.println("GASTO MAYOR: " + descripcion[posMayor] + " ("
                                + categoria[posMayor] + ") Bs " + monto[posMayor]
                                + " - " + fecha[posMayor]);
                        System.out.println("GASTO MENOR: " + descripcion[posMenor] + " ("
                                + categoria[posMenor] + ") Bs " + monto[posMenor]
                                + " - " + fecha[posMenor]);
                        System.out.println("Diferencia: Bs " + (monto[posMayor] - monto[posMenor]));
                    }
                    break;

                // ================= OPCION 8: SALIR =================
                case 8:
                    System.out.println("Resumen final:");
                    System.out.println("  Gastos registrados: " + cantidadGastos);
                    System.out.println("  Total gastado     : Bs " + totalGastado);
                    System.out.println("Gracias por usar el sistema. Hasta pronto.");
                    break;

                default:
                    System.out.println("Opcion no valida. Seleccione del 1 al 8.");
                    break;
            }

        } while (opcion != 8);   // do-while del menu principal

        sc.close();
    }
}
