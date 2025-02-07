# Folder Structure Generator Plugin

## Overview

Folder Structure Generator is a powerful plugin designed to help developers quickly set up and manage folder structures like Clean Architecture. It allows users to define custom structures using a simple JSON format within the settings panel, making it highly flexible and adaptable to different project architectures.

## Features

- **Predefined Templates**: Generate a Clean Architecture structure out of the box.
- **Customizable Structures**: Define your own folder structures using JSON.
- **User-Friendly Settings Panel**: Modify and manage folder structures with ease.
- **Supports Multiple Architectures**: Adaptable to various architectural patterns.

## Installation

1. Download and install the plugin.
2. Open your project and access the plugin settings in tools section.
3. Define or select a folder structure template.

## Usage

1. Right click on any folder in your project.
2. Select **Structured Folder** from **New** in the context menu.
3. Enter folder name (feature name).

## Default Clean Architecture Template

The plugin comes with a predefined Clean Architecture structure. Below is an example JSON configuration:

```json
{
  "domain": {
    "type": "d",
    "children": {
      "repository": { "type": "d" },
      "entities": { "type": "d" }
    }
  },
  "data": {
    "type": "d",
    "children": {
      "datasources": { "type": "d" },
      "dtoes": { "type": "d" },
      "repositories": { "type": "d" }
    }
  },
  "presentation": {
    "type": "d",
    "children": {
      "pages": {
        "type": "d",
        "children": {
          "page": { "type": "f", "ext": "dart" }
        }
      },
      "components": { "type": "d" },
      "providers": { "type": "d" }
    }
  }
}
```

## Customization

You can define your own structures by modifying the JSON configuration. The format follows these rules:

- **Folders**: Defined with `{ "type": "d" }`.
- **Files**: Defined with `{ "type": "f", "ext": "<extension>" }`.
- **Nested Folders**: Use the `children` property to define subdirectories.

Example:

```json
{
  "src": {
    "type": "d",
    "children": {
      "utils": { "type": "d" },
      "main": { "type": "f", "ext": "dart" }
    }
  }
}
```

## Contributing

1. Fork the repository.
2. Create a new branch (`feature/my-feature`).
3. Commit your changes.
4. Push to the branch.
5. Open a pull request.

## License

This project is licensed under the MIT License.

Copyright (c) 2025 Amin Nezampour

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files, to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software.

See the [LICENSE.md](./LICENSE.md) file for complete license details.

## Contact

For any issues or feature requests, feel free to open an issue or contact the developer.

[Amin Nezampour](https://aminnez.com/)

