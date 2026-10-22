# Contributing

Keep changes focused on the existing controller/service/repository exercise. Preserve request DTO boundaries and use the current dependencies before introducing additional infrastructure.

## Verification

Run the README's proposed wrapper commands and report actual results. The context smoke test and manual Postman requests do not establish CRUD coverage. Add focused regression tests for non-trivial changes.

Check required fields, empty/null tag elements, missing IDs, duplicate titles, case-insensitive full matches and any-tag semantics. For pagination, cover omitted parameters, values below one, beyond-end pages and extreme integer values. For updates, check ID/creationDate preservation and complete replacement semantics.

Use synthetic posts and distinguish mocked tests from actual HTTP execution. If changing storage/concurrency, test ID allocation and list/object access together rather than assuming copied lists are sufficient.

## Pull requests

Describe the problem, focused change, API-contract impact, checks run and remaining gaps. Keep generated target files and unrelated refactors out of commits. Follow [SECURITY.md](SECURITY.md) for sensitive reports.
