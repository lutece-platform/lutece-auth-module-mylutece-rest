/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.rest.util.constants;


/**
 *
 * MyLuteceRestConstants
 *
 */
public final class MyLuteceRestConstants
{
    // CONSTANTS
    public static final String SLASH = "/";
    public static final String MYLUTECE_PLUGIN_NAME = "mylutece";

    // PATHS
    public static final String PATH_WADL = "wadl";
    public static final String PATH_USER_ATTRIBUTES = "{user_guid}";
    public static final String PATH_USER_ATTRIBUTE = "{user_guid}/{attribute}";

    // PARAMETERS
    public static final String PARAMETER_USER_GUID = "user_guid";
    public static final String PARAMETER_ATTRIBUTE = "attribute";

    // TAGS
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
    public static final String TAG_USER_ATTRIBUTES = "user-attributes";
    public static final String TAG_USER_ATTRIBUTE = "user-attribute";
    public static final String TAG_USER_ATTRIBUTE_KEY = "user-attribute-key";
    public static final String TAG_USER_ATTRIBUTE_VALUE = "user-attribute-value";

    // MARKS
    public static final String MARK_BASE_URL = "base_url";

    // MESSAGES
    public static final String MESSAGE_MYLUTECE_REST = "MyLutece Rest - ";
    public static final String MESSAGE_INVALID_USER = "Invalid User.";

    // TEMPLATES
    public static final String TEMPLATE_WADL = "admin/plugins/mylutece/modules/rest/wadl.xml";

    /**
     * Private constructor
     */
    private MyLuteceRestConstants(  )
    {
    }
}
