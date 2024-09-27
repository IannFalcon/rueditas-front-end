package pe.edu.cibertec.rueditas_front_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.rueditas_front_end.dto.BuscarVehiculoRequestDTO;
import pe.edu.cibertec.rueditas_front_end.dto.BuscarVehiculoResponseDTO;
import pe.edu.cibertec.rueditas_front_end.viewmodels.BuscarVehiculoModel;

@Controller
@RequestMapping("/rueditas")
public class RueditasController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio (Model model) {
        BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel("00", "", "", "", 0, 0.0, "");
        model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
        return "inicio";
    }

    @PostMapping("/buscar-vehiculo")
    public String buscarVehiculo (@RequestParam("placa") String placa, Model model) {

        // Si no se ingresó una placa
        if (placa == null || placa.trim().isEmpty()) {

            // Retornamos un mensaje y redirigimos al inicio
            BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel(
                "01",
                "Error: Debe ingresar una placa.",
                "",
                "",
                0,
                0.0,
                ""
            );
            model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
            return "inicio";

        }

        // Creamos una variable que contenga la validación del formato de la placa
        String validarPlaca = "^[A-Z0-9]{3}-[A-Z0-9]{3}$";

        // Si lo placa no cumple con el formato enviado
        if (!placa.matches(validarPlaca)) {

            // Retornamos un mensaje y redirigimos al inicio
            BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel(
                "02",
                "Error: El formato de la placa debe ser XXX-XXX.",
                "",
                "",
                0,
                0.0,
                ""
            );
            model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
            return "inicio";

        }

        try {

            // Invocamos al servicio de busqueda
            String ruta_api = "http://localhost:8080/rueditas/buscar";

            // Creamos el objeto para la solicitud y enviamos los datos
            BuscarVehiculoRequestDTO buscarVehiculoRequestDTO = new BuscarVehiculoRequestDTO(placa);

            // Realizamos la solicitud al servidor
            BuscarVehiculoResponseDTO buscarVehiculoResponseDTO = restTemplate.postForObject(
                    ruta_api,
                    buscarVehiculoRequestDTO,
                    BuscarVehiculoResponseDTO.class
            );

            // Si la respuesta del servidor no es nula y el codigo es 00
            if (buscarVehiculoResponseDTO != null && buscarVehiculoResponseDTO.codigo().equals("00")) {

                // Traemos los datos del vehiculo
                BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel(
                    "00",
                    "El vehiculo con placa: " + placa + " fue encontrado.",
                    buscarVehiculoResponseDTO.marca(),
                    buscarVehiculoResponseDTO.modelo(),
                    buscarVehiculoResponseDTO.nroAsientos(),
                    buscarVehiculoResponseDTO.precio(),
                    buscarVehiculoResponseDTO.color()
                );
                model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
                return "resultados";

            } else {

                // Si no se encontro el vehiculo
                BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel(
                    "02",
                    "Ups... Vehiculo no encontrado.",
                    "",
                    "",
                    0,
                    0.0,
                    ""
                );
                model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
                return "inicio";

            }

        } catch (Exception e) {

            // Si fallo la comunicación con el servidor
            BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel(
                "99",
                "Error: Ocurrió un error durante la busqueda.",
                "",
                "",
                0,
                0.0,
                ""
            );
            model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
            return "inicio";

        }

    }

}
