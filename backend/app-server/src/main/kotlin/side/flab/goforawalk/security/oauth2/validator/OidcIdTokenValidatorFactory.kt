package side.flab.goforawalk.security.oauth2.validator

import side.flab.goforawalk.security.oauth2.OAuth2Provider

class OidcIdTokenValidatorFactory(
    validatorsSet: Set<OidcIdTokenValidator>
) {
    private val validatorsMap: Map<OAuth2Provider, OidcIdTokenValidator> =
        validatorsSet.associateBy { it.supports() }

    fun getValidatorBy(provider: OAuth2Provider): OidcIdTokenValidator {
        return validatorsMap[provider]
            ?: throw IllegalArgumentException("No validator found for provider: $provider")
    }
}