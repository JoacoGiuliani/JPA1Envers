package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("en marcha Joaquin");

        entityManager.getTransaction().begin();

        Cliente cliente = Cliente.builder()
                .nombre("Joaquín")
                .apellido("Giuliani")
                .dni(45532172L)
                .domicilio(Domicilio.builder()
                        .nombreCalle("Las Cañas")
                        .numero(1950)
                        .build())
                .build();


        cliente = entityManager.merge(cliente);

        Factura factura1 = Factura.builder()
                .numero(77)
                .fecha("4/09/2024")
                .cliente(cliente)
                .detalle(new ArrayList<>())
                .build();

        Categoria perecederos = Categoria.builder().denominacion("Perecederos").build();
        Categoria lacteos = Categoria.builder().denominacion("Lacteos").build();
        Categoria limpieza = Categoria.builder().denominacion("Limpieza").build();

        Articulo art1 = Articulo.builder()
                .cantidad(380)
                .denominacion("Danette sabor chocolate")
                .precio(950)
                .categorias(new ArrayList<>())
                .detalle(new ArrayList<>())
                .build();

        art1.getCategorias().add(perecederos);
        art1.getCategorias().add(lacteos);

        Articulo art2 = Articulo.builder()
                .cantidad(300)
                .denominacion("Detergente Magistral")
                .precio(3500)
                .categorias(new ArrayList<>())
                .detalle(new ArrayList<>())
                .build();

        art2.getCategorias().add(limpieza);

        DetalleFactura det1 = DetalleFactura.builder()
                .cantidad(2)
                .subtotal(760)
                .articulo(art1)
                .factura(factura1)
                .build();

        DetalleFactura det2 = DetalleFactura.builder()
                .cantidad(1)
                .subtotal(300)
                .articulo(art2)
                .factura(factura1)
                .build();

        factura1.getDetalle().add(det1);
        factura1.getDetalle().add(det2);

        factura1.setTotal(1060);

//        Cliente cliente = entityManager.find(Cliente.class,1L);
//        cliente.setNombre("NachoF7");
//        cliente.setApellido("MainClove");
//        entityManager.merge(cliente);

      entityManager.persist(factura1);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}