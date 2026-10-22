# Security and demo-use guidance

This is an in-memory coursework API, not a hardened publishing service. No security audit, supported-version programme or response deadline is established here.

The supplied endpoints have no application authentication, author ownership or authorisation. The author field is caller-provided text, not verified identity. Use synthetic content and do not expose this configuration with private records.

Request validation enforces non-blank main text fields and a non-empty tags list, not maximum sizes or valid individual tags. Pagination has no upper bound or overflow guard. The shared ArrayList and ordinary ID counter are unsynchronised. These are static source observations, not executed exploit results or fixes.

Restart loses all records. No durable audit, backup, recovery or moderation workflow is supplied. The ignore rules do not explicitly exclude a plain .env file; review staged files before adding future private configuration.

For sensitive concerns, use a verified private maintainer channel if available, or request one without publishing details. No dedicated reporting address or enabled private-reporting feature was confirmed. Redact private content and credentials from reproductions.
