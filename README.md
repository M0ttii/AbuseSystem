# AbuseSystem Common Module


### Class Structure
#### User
- represents user table in mongodb
```Java
_id
uuid
latest_name
latest_as
created_at
updated_at
```
#### Punishment
- represents the punishment table in mongodb
```Java
_id : #MongoDB object ID
player_id : #MongoDB object ID by Player
punisher_id : #MongoDB object ID by Punisher
type : #Punishment type [BAN, KICK, MUTE]
reason : #Punishment reason 
evidence : #Evidence for punishment if it's needed (A.e. Screenshot-Link, Video-Link)
active : #TRUE/FALSE if player is/is not currently banned or muted
expire_at : #Date on which punishment expires
created_at : #Date on which punishment was created
updated_at : #
```
#### Template
- represents the template table in mongodb
```Java
number : #Template ID (Also number for using this template)
name : #Template name
reason : #Punishment reason shown to the player
need_evidence : #TRUE if staff needs to attach a proof
punishments : #List of punishment layers
```

#### Template

#### How to use the API

##### Initialize the API 

```Java
Injector injector = Guice.createInjector(new AbuseSystemCommon(config));
userRepository = injector.getInstance(UserRepository.class);
punishmentRepository = injector.getInstance(punishmentRepository.class);
```

