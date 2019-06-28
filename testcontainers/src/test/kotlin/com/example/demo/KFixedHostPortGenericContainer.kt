package com.example.demo

import org.testcontainers.containers.FixedHostPortGenericContainer

class KFixedHostPortGenericContainer(imageName: String) :
    FixedHostPortGenericContainer<KFixedHostPortGenericContainer>(imageName)