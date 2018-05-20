# Math
A side project which implements RSA cryptography and other security protocols. 

General:
In this project I have written methods and objects which describe basic security protocols or algorithms which many privacy systems employ. In one package I have implemented basic RSA and will continue adding features to mimic modern RSA cryptosystems.

RSA:
In the RSA package there exists basic algorithms which RSA may rely upon. In addition there exists a Keypair object which holds both a public and private key. The key pair objet allows users to encrypt and decrypt messages with varying key sizes. Soon one would be able to sign a message with their private key for authentication. Also I hope to be able to make this key pair (and therefore public/private keys) serializable for storage and future use. There also exists a testing suite which ensures that the current iteration of the key pair object correctly encrypts and decrypts messages of varying lengths using various sized keys.

