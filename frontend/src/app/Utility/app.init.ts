import { KeycloakService } from 'keycloak-angular/public_api';
import { environment } from '../../environment/environment';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.url,
        realm: environment.realm,
        clientId: environment.clientid,
      },
      initOptions:{
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin+'/assets/silent-check-sso.html',
      },
    });
}
