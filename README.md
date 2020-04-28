# Java client for CSB
## Installation
1. Manually build the source and include the JAR/dependency in your project.
2. <<TODO>> Add MavenCentral repository link
## Usage 

#### Instantiation

Java client for CSB allows to send events to CSB APi in two ways:-
1. Synchronous
```
  CSB client = new CSB("https://{domain}.customersuccessbox.com",
            "{API_KEY}");
```
2. Asynchronous
```
  CSB client = new CSB("https://{domain}.customersuccessbox.com",
            "{API_KEY}",true //for asynchronous);
```
```Asynchronous client is inherently asynchronous and wont return any response in a sequential flow. You can execut the method and it will do its work in background thread.```

#### Events
```CSBRespone``` is the generic response class for API responses
##### Login
```
    CSBResponse response = cleint.login("{user_id}", "{account_id}");
```
##### User
```
    CSBResponse response = client.user("{user_id}", "{account_id}", <Map of Traits>);
    // Example
      CSBResponse response = client.user("{user_id}", "{account_id}",Collections.singletonMap("trait-key", "trait-value"));
```
##### Account
```
handle.account("128268183", Collections.singletonMap("sample-trait", "trait-value"));
```
##### Feature
```
CSBResponse response = client.feature("{user_id}", "{account_id}", "{product_id}", "{module_id}", "{feature_id}");
```
##### Logout
```
CSBResponse response = client.logout("{user_id}", "{account_id}");
```
