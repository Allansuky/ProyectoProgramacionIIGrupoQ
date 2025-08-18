package capalogica;

import java.util.List;

public class Calculos {

    public static void aplicarCalculos(CLFactura_Vehiculo factura, List<CLDetalleFactura> detalles) {
        double subtotal = 0;

        for (CLDetalleFactura d : detalles) {
            subtotal += d.getCantidad() * d.getPrecioUnitario();
        }

        double impuesto = subtotal * 0.15;
        double total = subtotal + impuesto;

        factura.setSubtotal(subtotal);
        factura.setImpuesto(impuesto);
        factura.setTotalPago(total);
    }
}
