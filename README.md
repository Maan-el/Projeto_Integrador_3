# Projeto_Integrador_2

JDK 21

## API 

É necessário adicionar a assinatura da API na JVM manualmente para que a conexãão possa ocorrer   


keytool -import -noprompt -trustcacerts -alias < AliasName > -file < Certificate > -keystore < KeystoreFile > -storepass < Password >

- \<AliasName> => Nome dado ao certificado adicionado
- \<Certificate> => Arquivo com o certificado SSL
- \<KeyStoreFile> => Local contendo as chaves da JVM ($JAVA_HOME/lib/security/cacerts) 
- \<Password> => Senha