# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### Unreleased

### Added

- UI: Added mobile layout
- BottomAppbar: Improved layout and design
- New Pages: Pages artists, characters and tags

### Improved

- New Gallery: The submissions now are shown by rows
- Filters: Changed Artist filter to Artists. Now you can filter by more then 1 artist.

### [1.0.1] - 2024-01-30

### Fixed

- Error with routes in pages
- Layout errors

### [1.0.0] - 2024-01-30

### Added

- Textfield with limited characters in all forms an edits
- Docker: Now artganizer frontend and backend runs in only 1 image.

### Fixed

- Submission: Hovering the mouse over the submission caused the animation to go through the top bar
- Separation in Artist and Character forms

## [0.9.1] - 2024-01-24

### Fixed

- Error when passing env variable to frontend

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
