package ru.ssau.springlab4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.springlab4.model.MedicalHistory;
import ru.ssau.springlab4.model.Owner;
import ru.ssau.springlab4.model.Pet;
import ru.ssau.springlab4.model.Veterinarian;
import ru.ssau.springlab4.service.MedicalHistoryService;
import ru.ssau.springlab4.service.OwnerService;
import ru.ssau.springlab4.service.PetService;
import ru.ssau.springlab4.service.VeterinarianService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/records")
public class VetController {

    private final MedicalHistoryService medicalHistoryService;
    private final OwnerService ownerService;
    private final PetService petService;
    private final VeterinarianService veterinarianService;

    @Autowired
    public VetController(MedicalHistoryService medicalHistoryService,
                            OwnerService ownerService,
                            PetService petService,
                            VeterinarianService veterinarianService) {
        this.medicalHistoryService = medicalHistoryService;
        this.ownerService = ownerService;
        this.petService = petService;
        this.veterinarianService = veterinarianService;
    }

    // 1. Отображение всех записей
    @GetMapping("/all")
    public String getAllRecords(Model model) {
        List<MedicalHistory> medicalHistories = medicalHistoryService.getAllMedicalHistories();

        model.addAttribute("medicalHistories", medicalHistories);

        return "records-all";
    }

    // 2. Отображение конкретной записи
    @GetMapping("/{id}")
    public String getRecordById(@PathVariable Long id, Model model) {
        MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistoryById(id);
        model.addAttribute("medicalHistory", medicalHistory);

        if (medicalHistory != null) {
            model.addAttribute("pet", medicalHistory.getPet());
            model.addAttribute("owner", medicalHistory.getPet().getOwners()); // Предполагается, что у каждого питомца есть владельцы
            model.addAttribute("veterinarian", medicalHistory.getPet().getVeterinarian());
        }

        return "record-detail";
    }

    // 3. Добавление новой записи
    @GetMapping("/add")
    public String showAddRecordForm(Model model) {
        List<Pet> pets = petService.getAllPets();
        List<Veterinarian> veterinarians = veterinarianService.getAllVeterinarians();
        List<Owner> owners = ownerService.getAllOwners();
        model.addAttribute("pets", pets);
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("owners", owners);
        return "record-add";
    }

    @PostMapping("/add")
    public String addRecord(@RequestParam String medicalHistory,
                            @RequestParam String firstVisitDate,
                            @RequestParam Long petId,
                            @RequestParam Long veterinarianId,
                            @RequestParam Long ownerId) {
        Pet pet = petService.getPetById(petId);
        Veterinarian veterinarian = veterinarianService.getVeterinarianById(veterinarianId);
        Owner owner = ownerService.getOwnerById(ownerId);

        if (pet != null && veterinarian != null && owner != null) {
            // Обновляем владельца и ветеринара у питомца
            pet.setVeterinarian(veterinarian);

            if(pet.getOwners() == null) {
                pet.setOwners(new HashSet<>());
                pet.getOwners().add(owner);
            } else pet.getOwners().add(owner);

            // Сохраняем изменения питомца
            petService.addPet(pet);

            // Создаем запись в медицинской истории и связываем её с питомцем
            MedicalHistory newRecord = new MedicalHistory();
            newRecord.setMedicalHistory(medicalHistory);
            newRecord.setFirstVisitDate(firstVisitDate);
            newRecord.setPet(pet);

            // Сохраняем медицинскую историю
            medicalHistoryService.addMedicalHistory(newRecord);
        }

        return "redirect:/records/all";
    }

    // Добавление нового питомца
    @GetMapping("/pet/add")
    public String showAddPetForm() {
        return "pet-add";
    }

    @PostMapping("/pet/add")
    public String addPet(@RequestParam String name,
                         @RequestParam String breed) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBreed(breed);
        petService.addPet(pet);
        return "redirect:/records/all";
    }

    // Добавление нового владельца
    @GetMapping("/owner/add")
    public String showAddOwnerForm() {
        return "owner-add";
    }

    @PostMapping("/owner/add")
    public String addOwner(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam Integer age) {
        Owner owner = new Owner();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAge(age);
        ownerService.addOwner(owner);
        return "redirect:/records/all";
    }

    // Добавление нового ветеринара
    @GetMapping("/veterinarian/add")
    public String showAddVeterinarianForm() {
        return "veterinarian-add";
    }

    @PostMapping("/veterinarian/add")
    public String addVeterinarian(@RequestParam String name,
                                  @RequestParam Integer age,
                                  @RequestParam String phone) {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setName(name);
        veterinarian.setAge(age);
        veterinarian.setPhone(phone);
        veterinarianService.addVeterinarian(veterinarian);
        return "redirect:/records/all";
    }
}