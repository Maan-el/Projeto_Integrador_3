# Guia de Configuração do Certificado SSL e Execução da Aplicação

1. Baixe o _jar_ mais recente na seção de [releases](https://github.com/Maan-el/Projeto_Integrador_3/releases)

2. Instale o [Java 21](https://www.oracle.com/br/java/technologies/downloads/)

3. Acesse o seguinte endereço eletrônico (recomendação, pelo Firefox): https://gtm.delary.dev/;

   A) Clique no cadeado presente na barra de URL;

   B) Clique na opção "Conexão segura";

   C) Clique na opção "Mais informações";

   D) Ao abrir uma janela, clique no botão, ver certificado;

   E) Ao abrir uma aba no navegador com informações dos certificados, desça até encontrar o link de download, "PEM (certificado)"

4. Após baixar o certificado abra o terminal na pasta do java, sem entrar no diretório /bin.

5. Insira o seguinte comando no terminal completando as informações que são necessárias para a adição da chave ao ecossistema Java.

  ``` Bash
    keytool -import -noprompt -trustcacerts -alias Chave -file $Certificate -keystore $KeystoreFile -storepass 123abc
  ```

$Certificate = diretório onde o certificado de segurança se encontra.

$KeystoreFile = lib/security/cacerts.

6. Depois de configurada a chave, execute o programa dentro da pasta onde se encontra, com o seguinte comando:

  ``` Bash
    java -jar $Nome-do-arquivo
  ```

7. Pronto, está funcionando.
