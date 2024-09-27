package pe.edu.cibertec.rueditas_front_end.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
