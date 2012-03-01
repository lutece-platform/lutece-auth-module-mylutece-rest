/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.mylutece.modules.rest.rs;

import fr.paris.lutece.plugins.mylutece.modules.rest.util.constants.MyLuteceRestConstants;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.plugins.rest.util.json.JSONUtil;
import fr.paris.lutece.plugins.rest.util.xml.XMLUtil;
import fr.paris.lutece.portal.service.security.UserAttributesService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.xml.XmlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


/**
 *
 * CRMRest
 *
 */
@Path( RestConstants.BASE_PATH + MyLuteceRestConstants.MYLUTECE_PLUGIN_NAME )
public class MyLuteceRest
{
    private UserAttributesService _userAttributesService;

    /**
     * Set the user attributes service
     * @param userAttributesService the user attributes service
     */
    public void setUserAttributesService( UserAttributesService userAttributesService )
    {
        _userAttributesService = userAttributesService;
    }

    /**
     * Get the wadl.xml content
     * @param request {@link HttpServletRequest}
     * @return the content of wadl.xml
     */
    @GET
    @Path( MyLuteceRestConstants.PATH_WADL )
    @Produces( MediaType.APPLICATION_XML )
    public String getWADL( @Context
    HttpServletRequest request )
    {
        StringBuilder sbBase = new StringBuilder( AppPathService.getBaseUrl( request ) );

        if ( sbBase.toString(  ).endsWith( MyLuteceRestConstants.SLASH ) )
        {
            sbBase.deleteCharAt( sbBase.length(  ) - 1 );
        }

        sbBase.append( RestConstants.BASE_PATH + MyLuteceRestConstants.MYLUTECE_PLUGIN_NAME );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MyLuteceRestConstants.MARK_BASE_URL, sbBase.toString(  ) );

        HtmlTemplate t = AppTemplateService.getTemplate( MyLuteceRestConstants.TEMPLATE_WADL, request.getLocale(  ),
                model );

        return t.getHtml(  );
    }

    /**
     * Get the CRMUser attributes in XML
     * @param strUserGuid the user guid
     * @return the CRMUser attributes
     */
    @GET
    @Path( MyLuteceRestConstants.PATH_USER_ATTRIBUTES )
    @Produces( MediaType.APPLICATION_XML )
    public String getMyLuteceUserAttributesXml( @PathParam( MyLuteceRestConstants.PARAMETER_USER_GUID )
    String strUserGuid )
    {
        StringBuffer sbXML = new StringBuffer(  );

        if ( StringUtils.isNotBlank( strUserGuid ) )
        {
            // sbXML.append( XmlUtil.getXmlHeader(  ) );
            sbXML.append( MyLuteceRestConstants.XML_HEADER );
            XmlUtil.beginElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTES );

            Map<String, String> listAttributes = _userAttributesService.getAttributes( strUserGuid );

            for ( Entry<String, String> attribute : listAttributes.entrySet(  ) )
            {
                XmlUtil.beginElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTE );
                XmlUtil.addElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTE_KEY, attribute.getKey(  ) );
                XmlUtil.addElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTE_VALUE, attribute.getValue(  ) );
                XmlUtil.endElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTE );
            }

            XmlUtil.endElement( sbXML, MyLuteceRestConstants.TAG_USER_ATTRIBUTES );
        }
        else
        {
            AppLogService.error( MyLuteceRestConstants.MESSAGE_MYLUTECE_REST +
                MyLuteceRestConstants.MESSAGE_INVALID_USER );
            sbXML.append( XMLUtil.formatError( MyLuteceRestConstants.MESSAGE_INVALID_USER, 3 ) );
        }

        return sbXML.toString(  );
    }

    /**
     * Get the CRMUser attributes in JSON
     * @param strUserGuid the user guid
     * @return the attributes
     */
    @GET
    @Path( MyLuteceRestConstants.PATH_USER_ATTRIBUTES )
    @Produces( MediaType.APPLICATION_JSON )
    public String getMyLuteceUserAttributesJson( @PathParam( MyLuteceRestConstants.PARAMETER_USER_GUID )
    String strUserGuid )
    {
        String strJSON = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strUserGuid ) )
        {
            JSONObject jsonAttributes = new JSONObject(  );
            JSONArray jsonArray = new JSONArray(  );

            Map<String, String> listAttributes = _userAttributesService.getAttributes( strUserGuid );

            for ( Entry<String, String> attribute : listAttributes.entrySet(  ) )
            {
                JSONObject jsonAttribute = new JSONObject(  );
                jsonAttribute.accumulate( MyLuteceRestConstants.TAG_USER_ATTRIBUTE_KEY, attribute.getKey(  ) );
                jsonAttribute.accumulate( MyLuteceRestConstants.TAG_USER_ATTRIBUTE_VALUE, attribute.getValue(  ) );
                jsonArray.add( jsonAttribute );
            }

            jsonAttributes.accumulate( MyLuteceRestConstants.TAG_USER_ATTRIBUTES, jsonArray );
            strJSON = jsonAttributes.toString( 4 );
        }
        else
        {
            AppLogService.error( MyLuteceRestConstants.MESSAGE_MYLUTECE_REST +
                MyLuteceRestConstants.MESSAGE_INVALID_USER );
            strJSON = JSONUtil.formatError( MyLuteceRestConstants.MESSAGE_INVALID_USER, 3 );
        }

        return strJSON;
    }

    /**
     * Get the CRMUser attribute value
     * @param strUserGuid the user guid
     * @param strAttribute the attribute
     * @return the attribute value
     */
    @GET
    @Path( MyLuteceRestConstants.PATH_USER_ATTRIBUTE )
    @Produces( MediaType.TEXT_PLAIN )
    public String getMyLuteceUserAttribute( @PathParam( MyLuteceRestConstants.PARAMETER_USER_GUID )
    String strUserGuid, @PathParam( MyLuteceRestConstants.PARAMETER_ATTRIBUTE )
    String strAttribute )
    {
        String strAttributeValue = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strUserGuid ) )
        {
            strAttributeValue = _userAttributesService.getAttribute( strUserGuid, strAttribute );
        }
        else
        {
            AppLogService.error( MyLuteceRestConstants.MESSAGE_MYLUTECE_REST +
                MyLuteceRestConstants.MESSAGE_INVALID_USER );
        }

        return strAttributeValue;
    }
}
