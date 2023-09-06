package com.giulianoespejo.Practico.Persistencia.en.JPA;

import com.giulianoespejo.Practico.Persistencia.en.JPA.Entity.*;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.Estado;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.FormaPago;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.TipoEnvio;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Enum.TipoProducto;
import com.giulianoespejo.Practico.Persistencia.en.JPA.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PracticoPersistenciaEnJpaApplication {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	RubroRepository rubroRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	DomicilioRepository domicilioRepository;
	@Autowired
	FacturaRepository facturaRepository;

	public static void main(String[] args) {
		SpringApplication.run(PracticoPersistenciaEnJpaApplication.class, args);
		System.out.println("Estoy funcionando en el diagrama de clases UML");
	}

	@Bean
	CommandLineRunner init(DomicilioRepository domicilioRepo){
		return args -> {
			Rubro rubro1 = Rubro.builder()
					.denominacion("Consumible")
					.build();

			Producto producto1 = Producto.builder()
					.denominacion("Consumible")
					.foto("nafta.png")
					.tipo(TipoProducto.INSUMO)
					.precioCompra(100.0)
					.precioVenta(150.0)
					.receta(".")
					.stockActual(40)
					.stockMinimo(5)
					.tiempoEstimadoCocina(0)
					.unidadMedida("litros")
					.build();
			rubro1.addProductos(producto1);
			rubroRepository.save(rubro1);

			DetallePedido detallePedido1 = DetallePedido.builder()
					.cantidad(1)
					.producto(producto1)
					.subtotal(150.0)
					.build();

			Pedido pedido1 = Pedido.builder()
					.fecha("05/09/2023")
					.total(150.0)
					.estado(Estado.PREPARACION)
					.horaEstimadaEntrega("10:54")
					.tipoEnvio(TipoEnvio.RETIRA)
					.build();
			pedido1.addDetallePedido(detallePedido1);


			Factura factura = Factura.builder()
					.numero(1)
					.formaPago(FormaPago.EFECTIVO)
					.descuento(0.0)
					.total(150)
					.fecha("05/09/2023")
					.build();
			pedido1.setFactura(factura);
			pedidoRepository.save(pedido1);

			Cliente cliente = Cliente.builder()
					.nombre("Giuliano")
					.apellido("Espejo")
					.email("giulianoespejo@gmail.com")
					.telefono("1234567897")
					.build();
			cliente.addPedido(pedido1);

			clienteRepository.save(cliente);

			Domicilio domicilio = Domicilio.builder()
					.calle("Suipacha")
					.numero("123")
					.localidad("MDZ")
					.cliente(cliente)
					.build();
			domicilio.addPedido(pedido1);
			domicilioRepository.save(domicilio);

			Usuario usuario = Usuario.builder()
					.nombre("GIULIANO-USER")
					.password("123456")
					.rol("CLIENT-ROLE")
					.build();
			usuario.addPedido(pedido1);
			usuarioRepository.save(usuario);

		};
	}
}
