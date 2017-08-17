#Bas Tools
Adds some extended features to PhpStorm.

&nbsp;


### General Features
- **UI/UX**
  - Darker interface.
  - Custom tabs.
  - More Material Design like LaF.

&nbsp;
 
### Completion
- **General**
  - Completion of values inside the `header` and `header_remove` functions.
  - Completion of filenames inside `file_get_contents`, `is_file` and more functions.
  - Completion of same parameters in child functions.

&nbsp;
 
### Inspections
- **General**
  - Check validity of callables.

- **Identity Framework**
  - Replace `add_action` with `Identity::action`. *Quick Fix available!*
  - Replace `do_action` with `Identity::actionDo`. *Quick Fix available!*
  - Replace `remove_action` with `Identity::actionRemove`. *Quick Fix available!*
  - Replace `add_filter` with `Identity::filter`. *Quick Fix available!*
  - Replace `apply_filters` with `Identity::filterApply`. *Quick Fix available!*
  - Replace `remove_filter` with `Identity::filterRemove`. *Quick Fix available!*
  
&nbsp;

### Intentions
- **Compute Constant Value**
  - Computes constant value of expressions (eg. 4 + 5 computes to 9).

&nbsp;

### Live Templates
- **General**
  - `ctor` creates a constructor inside a class.
  
- **Identity Framework**
  - `idty` resolves to `Identity::`.
  - `idtyb` resolves to `Identity::bootstrap()->`.
  - `idtyc` resolves to `Identity::core()->`.
  - `idtyle` resolves to `Identity::core()->loadExtension()`.
  - `idtyt` resolves to `Identity::template()->`.
  - `idtyr` resolves to `Identity::register()->`.
  - `idtyrg` resolves to `Identity::register()->get()`.
  - `idtyrs` resolves to `Identity::register()->set()`.
  - `idtya` resolves to `Identity::ajax()`.

&nbsp;

### Tool Windows
- **Date Time Helpers**
  - Current Unix Timestamp.
  - Current Date Time.
  - Convert Unix Timestamp to Date Time.
  - Convert Date Time to Unix Timestamp.

