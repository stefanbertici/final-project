import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function patternMatch(pattern: RegExp): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const valid = pattern.test(control.value);
    return valid ? null : {invalidPattern: true};
  };
}
