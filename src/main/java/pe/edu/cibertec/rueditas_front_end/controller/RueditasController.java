package pe.edu.cibertec.rueditas_front_end.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.rueditas_front_end.viewmodels.BuscarVehiculoModel;

@Controller
@RequestMapping("/rueditas")
public class RueditasController {

    @GetMapping("/inicio")
    public String inicio (Model model) {
        BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel("00", "");
        model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
        return "inicio";
    }

    @PostMapping("/buscar-vehiculo")
    public String buscarVehiculo (@RequestParam("placa") String placa, Model model) {

        // Si no se ingresó una placa
        if (placa == null || placa.trim().isEmpty()) {

            // Retornamos un mensaje y redirigimos al inicio
            BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel("01", "Error: Debe ingresar una placa.");
            model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
            return "inicio";

        }

        // Creamos una variable que contenga la validación del formato de la placa
        String validarPlaca = "^[A-Z0-9]{3}-[A-Z0-9]{3}$";

        // Si lo placa no cumple con el formato enviado
        if (!placa.matches(validarPlaca)) {

            // Retornamos un mensaje y redirigimos al inicio
            BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel("02", "Error: El formato de la placa debe ser XXX-XXX.");
            model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
            return "inicio";

        }

        BuscarVehiculoModel buscarVehiculoModel = new BuscarVehiculoModel("00", "La placa ingresada es válida.");
        model.addAttribute("buscarVehiculoModel", buscarVehiculoModel);
        return "resultados";

    }

}
