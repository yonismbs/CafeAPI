## Structure

Il existe un [module maven](https://maven.apache.org/guides/mini/guide-multiple-modules.html) dans le projet, vous pouvez lire
la procédure d'exécution plus loin.

## Notes sur les technologies utilisées

* [Jetty](https://www.eclipse.org/jetty/) est un Servlet Container. Il accepte les requêtes du web et sait comment répondre.
* [Jersey](https://jersey.github.io/) est un Servlet fait pour le développement d'API REST. Il sait comment faire la correspondance entre un API REST et vos méthodes selon la norme JAX-RS.
* [Jackson](https://www.baeldung.com/jackson) sert à sérialiser/désérialiser les objets JSON en POJO.

## Intégration Docker


Un dockerfile est fourni et peut être utilisé à l'aide des commandes suivantes:

```bash
docker build -t application-glo4002 .
docker run -p 8181:8181 application-glo4002
```

## Démarrer le projet

Vous pouvez démarrer le serveur (CafeServer).

Le `main()` ne demande pas d'argument.

Vous pouvez également rouler le serveur via maven :

```bash
mvn clean install
mvn exec:java -pl cafe-api
```

Une resource "heartbeat" vous est fournis pour tester que le service démarre bien. 
Allez à l'URL `http://localhost:8181/heartbeat?token=unique_token` pour le valider.
