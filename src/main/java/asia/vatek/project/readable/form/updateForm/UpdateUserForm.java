package asia.vatek.project.readable.form.updateForm;

import asia.vatek.project.readable.form.createForm.CreateUserForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateUserForm extends CreateUserForm {

    @NotNull
    private Long id;
}
