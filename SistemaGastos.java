import java.util.Scanner;

/*
 * ============================================================
 *  SISTEMA DE CONTROL DE GASTOS PERSONALES
 *  Proyecto Integrador - Programacion I
 *  Sprint 2  (version MODULAR, sin vectores)
 * ------------------------------------------------------------
 *  Evidencia del Sprint 2:
 *   - Metodos (procedimientos y funciones)
 *   - Parametros y valores de retorno (numerico, logico y texto)
 *   - Ambito de variables (globales de clase, locales y parametros)
 *   - Reutilizacion de modulos
 *   - Estructuras condicionales y repetitivas DENTRO de los metodos
 *
 *  Se conserva todo lo del Sprint 1. Por indicacion del Product
 *  Owner, los vectores (arreglos) recien se aplican en el Sprint 3.
 * ============================================================
 */
public class SistemaGastos {

    // ================================================================
    //  VARIABLES GLOBALES (ambito de clase: visibles en todos los metodos)
    // ================================================================
    static Scanner sc = new Scanner(System.in);

    // Datos del usuario
    static String  nombreUsuario      = "";      // cadena
    static double  presupuestoMensual = 0;       // decimal
    static boolean usuarioRegistrado  = false;   // logico

    // Contador y acumuladores generales
    static int    cantidadGastos  = 0;   // CONTADOR de gastos
    static double totalGastado    = 0;   // ACUMULADOR de montos
    static String historialGastos = "";  // ACUMULADOR de texto (lista general)

    // Datos de cada categoria: contador, acumulador y lista de texto
    static int cantAlimentacion = 0;     static double subAlimentacion = 0;     static String histAlimentacion = "";
    static int cantTransporte = 0;       static double subTransporte = 0;       static String histTransporte = "";
    static int cantVivienda = 0;         static double subVivienda = 0;         static String histVivienda = "";
    static int cantEducacion = 0;        static double subEducacion = 0;        static String histEducacion = "";
    static int cantSalud = 0;            static double subSalud = 0;            static String histSalud = "";
    static int cantEntretenimiento = 0;  static double subEntretenimiento = 0;  static String histEntretenimiento = "";
    static int cantOtros = 0;            static double subOtros = 0;            static String histOtros = "";

    // Gasto mayor y gasto menor
    static double mayorMonto = 0;
    static String mayorDescripcion = "", mayorCategoria = "", mayorFecha = "";
    static double menorMonto = 0;
    static String menorDescripcion = "", menorCategoria = "", menorFecha = "";

    // Mensaje reutilizado cuando todavia no hay gastos (constante global)
    static final String AVISO_SIN_GASTOS =
            "Aun no ha registrado gastos. Use la opcion 3 para registrar el primero.";

    // ================================================================
    //  METODO PRINCIPAL: solo coordina, no realiza tareas
    // ================================================================
    public static void main(String[] args) {
        mostrarMenu();
        sc.close();
    }

    // ================================================================
    //  1) MODULO COORDINADOR DEL MENU
    //     Procedimiento SIN parametros y SIN valor de retorno
    //     Estructuras: do-while + switch
    // ================================================================
    static void mostrarMenu() {
        int opcion;   // variable LOCAL del metodo

        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("  CONTROL DE GASTOS PERSONALES");
            System.out.println("=================================");
            System.out.println("1. Registrar mi nombre y presupuesto");
            System.out.println("2. Ver mis datos registrados");
            System.out.println("3. Registrar un gasto");
            System.out.println("4. Ver la lista de mis gastos");
            System.out.println("5. Ver el resumen de mis gastos");
            System.out.println("6. Ver mis gastos por categoria");
            System.out.println("7. Ver mi gasto mas alto y mas bajo");
            System.out.println("8. Ver la categoria en la que mas gasto");
            System.out.println("9. Salir");
            System.out.println("=================================");

            opcion = leerEntero("Seleccione una opcion (1 a 9): ");

            switch (opcion) {
                case 1: registrarUsuario();            break;
                case 2: mostrarUsuario();              break;
                case 3: registrarGasto();              break;
                case 4: mostrarListaGastos();          break;
                case 5: mostrarReporte();              break;
                case 6: mostrarGastosPorCategoria();   break;
                case 7: mostrarMayorYMenor();          break;
                case 8: mostrarCategoriaMayorGasto();  break;
                case 9: mostrarResumenFinal();         break;
                default:
                    System.out.println("Opcion no valida. Seleccione un numero del 1 al 9.");
                    break;
            }

        } while (opcion != 9);
    }

    // ================================================================
    //  2) MODULOS DE ENTRADA Y VALIDACION (funciones con retorno)
    // ================================================================

    // Funcion que DEVUELVE UN NUMERO ENTERO. Valida con while.
    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Entrada invalida. Ingrese un numero entero: ");
        }
        int valor = sc.nextInt();   // variable LOCAL
        sc.nextLine();              // limpia el salto de linea pendiente
        return valor;
    }

    // Funcion con VARIOS PARAMETROS que REUTILIZA leerEntero(). Valida con do-while.
    static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int valor;
        do {
            valor = leerEntero(mensaje);
            if (valor < minimo || valor > maximo) {
                System.out.println("Valor fuera de rango. Ingrese un numero entre "
                        + minimo + " y " + maximo + ".");
            }
        } while (valor < minimo || valor > maximo);
        return valor;
    }

    // Funcion que DEVUELVE UN NUMERO DECIMAL mayor a cero.
    static double leerDecimalPositivo(String mensaje) {
        double valor;
        do {
            System.out.print(mensaje);
            while (!sc.hasNextDouble()) {
                sc.next();
                System.out.print("Monto invalido. Ingrese un numero: ");
            }
            valor = sc.nextDouble();
            sc.nextLine();

            if (valor <= 0) {
                System.out.println("El valor debe ser mayor a 0.");
            }
        } while (valor <= 0);
        return valor;
    }

    // Funcion que DEVUELVE UN TEXTO que no puede quedar vacio.
    static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine();
            if (texto.trim().isEmpty()) {
                System.out.println("Este dato no puede quedar vacio.");
            }
        } while (texto.trim().isEmpty());
        return texto.trim();
    }

    // ================================================================
    //  3) MODULOS DE PRESENTACION REUTILIZABLES
    // ================================================================

    // Procedimiento CON parametro: imprime cualquier titulo con el mismo formato.
    static void mostrarTitulo(String titulo) {
        System.out.println();
        System.out.println("===== " + titulo.toUpperCase() + " =====");
    }

    // Procedimiento sin parametros: lista las 7 categorias disponibles.
    static void mostrarCategorias() {
        System.out.println("Categorias disponibles:");
        System.out.println("  1. Alimentacion   2. Transporte   3. Vivienda");
        System.out.println("  4. Educacion      5. Salud        6. Entretenimiento");
        System.out.println("  7. Otros");
    }

    // Funcion de formato: devuelve el monto con dos decimales (mejora pedida en el Sprint 1).
    static String bs(double monto) {
        return "Bs " + String.format("%.2f", monto);
    }

    // Funcion numerica: redondea a dos decimales.
    static double redondear(double valor) {
        return Math.round(valor * 100) / 100.0;
    }

    // ================================================================
    //  4) MODULOS DE CONTROL DE ESTADO (devuelven un valor LOGICO)
    // ================================================================

    static boolean hayUsuario() {
        if (!usuarioRegistrado) {
            System.out.println("Primero debe registrar su nombre y presupuesto (opcion 1).");
            return false;
        }
        return true;
    }

    static boolean hayGastos() {
        if (cantidadGastos == 0) {
            System.out.println(AVISO_SIN_GASTOS);
            return false;
        }
        return true;
    }

    // ================================================================
    //  5) MODULOS DE REGISTRO
    // ================================================================

    // Procedimiento: solicita y guarda los datos del usuario.
    static void registrarUsuario() {
        mostrarTitulo("Registro de usuario");
        nombreUsuario      = leerTextoNoVacio("Nombre del usuario: ");
        presupuestoMensual = leerDecimalPositivo("Presupuesto mensual (Bs): ");
        usuarioRegistrado  = true;

        System.out.println("Usuario registrado correctamente.");
        mostrarUsuario();   // REUTILIZACION del modulo de presentacion
    }

    // Procedimiento SIN valor de retorno: presenta los datos registrados.
    static void mostrarUsuario() {
        if (!hayUsuario()) {
            return;
        }
        mostrarTitulo("Mis datos registrados");
        System.out.println("  Usuario            : " + nombreUsuario);
        System.out.println("  Presupuesto mensual: " + bs(presupuestoMensual));
        System.out.println("  Gastos registrados : " + cantidadGastos);
        System.out.println("  Total gastado      : " + bs(totalGastado));

        if (dentroDelPresupuesto(totalGastado, presupuestoMensual)) {
            System.out.println("  Saldo disponible   : " + bs(presupuestoMensual - totalGastado));
        } else {
            System.out.println("  Presupuesto excedido en: " + bs(totalGastado - presupuestoMensual));
        }
    }

    // Procedimiento: registra un gasto completo apoyandose en otros modulos.
    static void registrarGasto() {
        if (!hayUsuario()) {
            return;
        }

        mostrarTitulo("Registro de gasto de " + nombreUsuario);

        String descripcion = leerTextoNoVacio("Descripcion del gasto: ");
        mostrarCategorias();
        int opcionCategoria = leerEnteroEnRango("Seleccione la categoria (1 a 7): ", 1, 7);
        double monto        = leerDecimalPositivo("Monto del gasto (Bs): ");
        String fecha        = leerTextoNoVacio("Fecha (dd/mm/aaaa): ");

        String categoria = nombreCategoria(opcionCategoria);
        String linea     = "   - " + descripcion + " | " + fecha + " | " + bs(monto) + "\n";

        guardarEnCategoria(opcionCategoria, monto, linea);
        actualizarMayorYMenor(descripcion, categoria, monto, fecha);

        totalGastado   = totalGastado + monto;     // ACUMULADOR
        cantidadGastos = cantidadGastos + 1;       // CONTADOR
        historialGastos = historialGastos + cantidadGastos + ". " + descripcion
                + " | " + categoria + " | " + fecha + " | " + bs(monto) + "\n";

        System.out.println();
        System.out.println("Gasto registrado a nombre de " + nombreUsuario + ":");
        System.out.println("  Descripcion : " + descripcion);
        System.out.println("  Categoria   : " + categoria);
        System.out.println("  Monto       : " + bs(monto));
        System.out.println("  Fecha       : " + fecha);
        System.out.println("Gastos registrados hasta ahora: " + cantidadGastos);

        if (dentroDelPresupuesto(totalGastado, presupuestoMensual)) {
            System.out.println("Saldo disponible: " + bs(presupuestoMensual - totalGastado));
        } else {
            System.out.println("ALERTA: supero su presupuesto en "
                    + bs(totalGastado - presupuestoMensual));
        }
    }

    // ================================================================
    //  6) MODULOS DE CATEGORIA (switch encapsulado, un metodo por dato)
    // ================================================================

    // Funcion que devuelve TEXTO: el nombre de la categoria elegida.
    static String nombreCategoria(int opcion) {
        String nombre;
        switch (opcion) {
            case 1:  nombre = "Alimentacion";    break;
            case 2:  nombre = "Transporte";      break;
            case 3:  nombre = "Vivienda";        break;
            case 4:  nombre = "Educacion";       break;
            case 5:  nombre = "Salud";           break;
            case 6:  nombre = "Entretenimiento"; break;
            default: nombre = "Otros";           break;
        }
        return nombre;
    }

    // Procedimiento con parametros: suma el gasto a su categoria.
    static void guardarEnCategoria(int opcion, double monto, String linea) {
        switch (opcion) {
            case 1:
                cantAlimentacion = cantAlimentacion + 1;
                subAlimentacion  = subAlimentacion + monto;
                histAlimentacion = histAlimentacion + linea;
                break;
            case 2:
                cantTransporte = cantTransporte + 1;
                subTransporte  = subTransporte + monto;
                histTransporte = histTransporte + linea;
                break;
            case 3:
                cantVivienda = cantVivienda + 1;
                subVivienda  = subVivienda + monto;
                histVivienda = histVivienda + linea;
                break;
            case 4:
                cantEducacion = cantEducacion + 1;
                subEducacion  = subEducacion + monto;
                histEducacion = histEducacion + linea;
                break;
            case 5:
                cantSalud = cantSalud + 1;
                subSalud  = subSalud + monto;
                histSalud = histSalud + linea;
                break;
            case 6:
                cantEntretenimiento = cantEntretenimiento + 1;
                subEntretenimiento  = subEntretenimiento + monto;
                histEntretenimiento = histEntretenimiento + linea;
                break;
            default:
                cantOtros = cantOtros + 1;
                subOtros  = subOtros + monto;
                histOtros = histOtros + linea;
                break;
        }
    }

    // Funcion numerica: cuantos gastos tiene la categoria.
    static int cantidadDeCategoria(int opcion) {
        int cantidad;
        switch (opcion) {
            case 1:  cantidad = cantAlimentacion;    break;
            case 2:  cantidad = cantTransporte;      break;
            case 3:  cantidad = cantVivienda;        break;
            case 4:  cantidad = cantEducacion;       break;
            case 5:  cantidad = cantSalud;           break;
            case 6:  cantidad = cantEntretenimiento; break;
            default: cantidad = cantOtros;           break;
        }
        return cantidad;
    }

    // Funcion numerica: subtotal acumulado de la categoria.
    static double subtotalDeCategoria(int opcion) {
        double subtotal;
        switch (opcion) {
            case 1:  subtotal = subAlimentacion;    break;
            case 2:  subtotal = subTransporte;      break;
            case 3:  subtotal = subVivienda;        break;
            case 4:  subtotal = subEducacion;       break;
            case 5:  subtotal = subSalud;           break;
            case 6:  subtotal = subEntretenimiento; break;
            default: subtotal = subOtros;           break;
        }
        return subtotal;
    }

    // Funcion de texto: lista de gastos de la categoria.
    static String listaDeCategoria(int opcion) {
        String lista;
        switch (opcion) {
            case 1:  lista = histAlimentacion;    break;
            case 2:  lista = histTransporte;      break;
            case 3:  lista = histVivienda;        break;
            case 4:  lista = histEducacion;       break;
            case 5:  lista = histSalud;           break;
            case 6:  lista = histEntretenimiento; break;
            default: lista = histOtros;           break;
        }
        return lista;
    }

    // Procedimiento con 4 parametros: mantiene el gasto mayor y el menor.
    static void actualizarMayorYMenor(String descripcion, String categoria,
                                      double monto, String fecha) {
        if (cantidadGastos == 0) {
            mayorMonto = monto;  mayorDescripcion = descripcion;
            mayorCategoria = categoria;  mayorFecha = fecha;

            menorMonto = monto;  menorDescripcion = descripcion;
            menorCategoria = categoria;  menorFecha = fecha;
        } else {
            if (monto > mayorMonto) {
                mayorMonto = monto;  mayorDescripcion = descripcion;
                mayorCategoria = categoria;  mayorFecha = fecha;
            }
            if (monto < menorMonto) {
                menorMonto = monto;  menorDescripcion = descripcion;
                menorCategoria = categoria;  menorFecha = fecha;
            }
        }
    }

    // ================================================================
    //  7) MODULOS DE CALCULO (parametros y valores de retorno)
    // ================================================================

    // Devuelve un RESULTADO NUMERICO y evita la division entre cero.
    static double calcularPromedio(double total, int cantidad) {
        if (cantidad == 0) {
            return 0;
        }
        return redondear(total / cantidad);
    }

    // Devuelve un RESULTADO NUMERICO: porcentaje de una parte sobre un total.
    static double calcularPorcentaje(double parte, double total) {
        if (total <= 0) {
            return 0;
        }
        return redondear((parte * 100) / total);
    }

    // Devuelve un VALOR LOGICO: equivalente a la verificacion de aprobacion.
    static boolean dentroDelPresupuesto(double total, double presupuesto) {
        return total <= presupuesto;
    }

    // Devuelve TEXTO: clasifica el nivel de gasto segun el porcentaje consumido.
    static String clasificarNivelGasto(double porcentaje) {
        String nivel;
        if (porcentaje <= 50) {
            nivel = "BAJO";
        } else if (porcentaje <= 80) {
            nivel = "MODERADO";
        } else if (porcentaje <= 100) {
            nivel = "ALTO";
        } else {
            nivel = "EXCEDIDO";
        }
        return nivel;
    }

    // Devuelve TEXTO: barra de avance de 10 bloques construida con un for.
    static String barraAvance(double porcentaje) {
        int bloques = (int) (porcentaje / 10);
        if (bloques > 10) {
            bloques = 10;
        }

        String barra = "[";
        for (int b = 1; b <= 10; b++) {
            if (b <= bloques) {
                barra = barra + "#";
            } else {
                barra = barra + "-";
            }
        }
        return barra + "]";
    }

    // ================================================================
    //  8) MODULOS DE REPORTE
    // ================================================================

    static void mostrarListaGastos() {
        if (!hayGastos()) {
            return;
        }
        mostrarTitulo("Listado de gastos de " + nombreUsuario);
        System.out.println("Presupuesto mensual: " + bs(presupuestoMensual));
        System.out.println("N. DESCRIPCION | CATEGORIA | FECHA | MONTO");
        System.out.println("----------------------------------------------------");
        System.out.print(historialGastos);
        System.out.println("----------------------------------------------------");
        System.out.println("Gastos registrados: " + cantidadGastos);
        System.out.println("Total gastado     : " + bs(totalGastado));
        System.out.println("Saldo disponible  : " + bs(presupuestoMensual - totalGastado));
    }

    // Equivale a mostrarReporte(): usa los modulos de calculo y clasificacion.
    static void mostrarReporte() {
        if (!hayGastos()) {
            return;
        }
        double promedio   = calcularPromedio(totalGastado, cantidadGastos);
        double porcentaje = calcularPorcentaje(totalGastado, presupuestoMensual);

        mostrarTitulo("Resumen de los gastos de " + nombreUsuario);
        System.out.printf("Gastos registrados    : %d%n", cantidadGastos);
        System.out.printf("Total gastado         : Bs %.2f%n", totalGastado);
        System.out.printf("Promedio por gasto    : Bs %.2f%n", promedio);
        System.out.printf("Presupuesto mensual   : Bs %.2f%n", presupuestoMensual);
        System.out.printf("Presupuesto consumido : %.2f %%%n", porcentaje);
        System.out.println("Avance                : " + barraAvance(porcentaje));
        System.out.println("Nivel de gasto        : " + clasificarNivelGasto(porcentaje));

        if (dentroDelPresupuesto(totalGastado, presupuestoMensual)) {
            System.out.println("Estado                : DENTRO DEL PRESUPUESTO");
        } else {
            System.out.println("Estado                : PRESUPUESTO EXCEDIDO");
        }
    }

    static void mostrarGastosPorCategoria() {
        if (!hayGastos()) {
            return;
        }
        mostrarTitulo("Gastos por categoria");
        mostrarCategorias();
        int elegida = leerEnteroEnRango("Elija la categoria que desea ver (1 a 7): ", 1, 7);

        String nombre   = nombreCategoria(elegida);        // REUTILIZACION
        int encontrados = cantidadDeCategoria(elegida);    // CONTADOR de la categoria
        double subtotal = subtotalDeCategoria(elegida);    // ACUMULADOR de la categoria
        String lista    = listaDeCategoria(elegida);

        mostrarTitulo("Gastos de la categoria: " + nombre);
        if (encontrados == 0) {
            System.out.println("No hay gastos registrados en la categoria " + nombre + ".");
        } else {
            System.out.print(lista);
            System.out.println("Gastos encontrados: " + encontrados);
            System.out.println("Subtotal          : " + bs(subtotal));
            System.out.printf("Representa el %.2f %% de su gasto total.%n",
                    calcularPorcentaje(subtotal, totalGastado));
        }
    }

    static void mostrarMayorYMenor() {
        if (!hayGastos()) {
            return;
        }
        mostrarTitulo("Mi gasto mas alto y mas bajo");
        System.out.println("Su gasto mas alto fue:");
        System.out.println("   " + mayorDescripcion + " (" + mayorCategoria + ")");
        System.out.println("   " + bs(mayorMonto) + " el " + mayorFecha);
        System.out.println("Su gasto mas bajo fue:");
        System.out.println("   " + menorDescripcion + " (" + menorCategoria + ")");
        System.out.println("   " + bs(menorMonto) + " el " + menorFecha);

        if (cantidadGastos == 1) {
            System.out.println("Solo tiene un gasto registrado, por eso es el mas alto y el mas bajo.");
        } else {
            System.out.println("Diferencia entre ambos: " + bs(mayorMonto - menorMonto));
        }
    }

    // Recorre las 7 categorias con un for reutilizando los modulos de categoria.
    static void mostrarCategoriaMayorGasto() {
        if (!hayGastos()) {
            return;
        }
        mostrarTitulo("Categoria en la que mas gasto");

        String catMayor = nombreCategoria(1);
        double subMayor = subtotalDeCategoria(1);

        for (int i = 2; i <= 7; i++) {
            if (subtotalDeCategoria(i) > subMayor) {
                subMayor = subtotalDeCategoria(i);
                catMayor = nombreCategoria(i);
            }
        }

        System.out.println("Usted gasta mas en   : " + catMayor);
        System.out.println("Subtotal de la misma : " + bs(subMayor));
        System.out.printf("Representa el %.2f %% de su gasto total.%n",
                calcularPorcentaje(subMayor, totalGastado));
    }

    static void mostrarResumenFinal() {
        mostrarTitulo("Resumen final");
        if (!usuarioRegistrado) {
            System.out.println("No se registro ningun usuario en esta sesion.");
        } else {
            System.out.println("  Usuario           : " + nombreUsuario);
            System.out.println("  Gastos registrados: " + cantidadGastos);
            System.out.println("  Total gastado     : " + bs(totalGastado));
            System.out.println("  Nivel de gasto    : "
                    + clasificarNivelGasto(calcularPorcentaje(totalGastado, presupuestoMensual)));
        }
        System.out.println("Gracias por usar el sistema. Hasta pronto.");
    }
}
