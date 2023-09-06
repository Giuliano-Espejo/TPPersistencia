package com.giulianoespejo.Practico.Persistencia.en.JPA.Entity;

import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.Estado;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.TipoEnvio;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Pedido extends BaseEntidad{

    private String fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String horaEstimadaEntrega;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<>();

    public void addDetallePedido(DetallePedido detallePedido){
        detallePedidos.add(detallePedido);
    }


}
