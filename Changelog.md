# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.9.0] - 2024-01-24

### Fixed

- Error that removes submissions when artist is removed
- Layout in submission view
- Changed Autocomplete for Tags Filter

### Added

- Change components to select Artist, Character and Tags
- Using Tab when selecting Artist, Characters or Tags, creates them.
- Page 404 when path does not exists

### Improved

- Submission form wider
- Changed order of Ok and Cancel buttons in forms
- Limited type of file to upload when uploading image

## [0.6.2] - 2023-09-18

### Fixed

- Fix problem with CORS policy

### Changed

- Tag Select uses Autocomplete from Material UI
- Character select and artist select now don't use a popper

## [0.6.1] - 2023-09-15

### Fixed

- Removed cors

## [0.6] - 2023-09-15

### Added

- Bottom submission navigation in the submission page
- Button to create an artist or a character in left panel accordions

### Fixed

- Error that changed the submission image when you try to create a character or artist in the submission form.
- When a form is cancelled or accepted, it will reset the form.

### Changed

- Artists and characters left lists are now sorted alphabetically
- Reorganized the submission form
- In all the forms the ok buttom is disabled unless it is introduced the needed information.

## [0.5] - 2023-09-07

First release of the app, it may have errors and optimization problems
