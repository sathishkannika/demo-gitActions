# Security Policy

## Supported Versions

| Version | Supported |
|---|---|
| 1.x (latest) | ✅ Active support |
| < 1.0        | ❌ No longer supported |

## Reporting a Vulnerability

**Please do NOT open a public GitHub issue for security vulnerabilities.**

Report vulnerabilities privately using one of the following methods:

### Option 1 — GitHub Private Security Advisory (preferred)
1. Go to the **Security** tab of this repository
2. Click **"Report a vulnerability"**
3. Fill in the details and submit

### Option 2 — Email
Send details to: **security@your-org.com**

Include:
- Description of the vulnerability
- Steps to reproduce
- Affected versions
- Any suggested fix (optional)

## What to Expect

| Timeline | Action |
|---|---|
| Within 48 hours | Acknowledgement of your report |
| Within 7 days   | Initial assessment and severity rating |
| Within 30 days  | Patch released (critical issues prioritised) |
| After patch     | Public disclosure coordinated with reporter |

## Scope

The following are **in scope**:
- The Spring Boot application code in this repository
- Dependencies bundled in the release JAR
- Authentication and authorisation logic
- API endpoint security

The following are **out of scope**:
- Vulnerabilities in infrastructure managed separately
- Denial-of-service attacks requiring significant resources
- Social engineering

## Security Tooling in CI

This project runs automated security checks on every PR:
- **OWASP Dependency-Check** — flags CVEs ≥ CVSS 7 (HIGH) in dependencies
- **SpotBugs** — detects security-related code patterns
- **JaCoCo** — enforces ≥ 80% test coverage to reduce untested code paths

Thank you for helping keep this project secure.
