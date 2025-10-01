package com.mottu.mottuguard.web.controller;

import com.mottu.mottuguard.repository.*;
import com.mottu.mottuguard.services.*;
import com.mottu.mottuguard.enums.*;
import com.mottu.mottuguard.models.*;
import com.mottu.mottuguard.web.dto.AssignTagForm;
import com.mottu.mottuguard.web.dto.MotoForm;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult; import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller @RequestMapping("/motos")
public class MotoController {
    private final MotoRepo motos; private final UwbTagRepo tags; private final MotoService svc;
    public MotoController(MotoRepo m, UwbTagRepo t, MotoService s){this.motos=m; this.tags=t; this.svc=s;}

    @GetMapping
    public String list(@RequestParam(required=false) MotoStatus status, Model model){
        var data = (status==null) ? motos.findAll() : motos.findByStatus(status);
        model.addAttribute("motos", data); model.addAttribute("status", status); return "motos/list"; }

    @GetMapping("/new") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String createForm(Model m){ m.addAttribute("form", new MotoForm()); m.addAttribute("modelos", ModeloMoto.values()); m.addAttribute("statuses", MotoStatus.values()); return "motos/form"; }

    @PostMapping @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String create(@ModelAttribute("form") @Valid MotoForm f, BindingResult br){ if(br.hasErrors()) return "motos/form";
        Moto m = new Moto(); m.setChassi(f.getChassi()); m.setPlaca(f.getPlaca()); m.setModelo(f.getModelo()); m.setStatus(f.getStatus());
        motos.save(m); return "redirect:/motos"; }

    @GetMapping("/{id}/edit") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String edit(@PathVariable Long id, Model model){ var m = motos.findById(id).orElseThrow(); var f = new MotoForm();
        f.setId(m.getId()); f.setChassi(m.getChassi()); f.setPlaca(m.getPlaca()); f.setModelo(m.getModelo()); f.setStatus(m.getStatus());
        model.addAttribute("form", f); model.addAttribute("modelos", ModeloMoto.values()); model.addAttribute("statuses", MotoStatus.values()); return "motos/form"; }

    @PostMapping("/{id}") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String update(@PathVariable Long id, @ModelAttribute("form") @Valid MotoForm f, BindingResult br){ if(br.hasErrors()) return "motos/form";
        Moto m = motos.findById(id).orElseThrow(); m.setChassi(f.getChassi()); m.setPlaca(f.getPlaca()); m.setModelo(f.getModelo()); m.setStatus(f.getStatus());
        return "redirect:/motos"; }

    @PostMapping("/{id}/delete") @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id){ motos.deleteById(id); return "redirect:/motos"; }

    @PostMapping("/{id}/reservar") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String reservar(@PathVariable Long id){ svc.reservar(id); return "redirect:/motos"; }
    @PostMapping("/{id}/liberar") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String liberar(@PathVariable Long id){ svc.liberar(id); return "redirect:/motos"; }

    @GetMapping("/{id}/assign") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String assignForm(@PathVariable Long id, Model model){ var f=new AssignTagForm(); f.setMotoId(id); model.addAttribute("form", f); model.addAttribute("tags", tags.findByMotoIsNull()); return "motos/assignTag"; }
    @PostMapping("/{id}/assign") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String assign(@PathVariable Long id, @ModelAttribute("form") @Valid AssignTagForm f, BindingResult br){ if(br.hasErrors()) return "motos/assignTag"; svc.assignTag(id, f.getTagId()); return "redirect:/motos"; }
    @PostMapping("/{id}/unassign") @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public String unassign(@PathVariable Long id){ svc.unassignTag(id); return "redirect:/motos"; }
}
