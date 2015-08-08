/*******************************************************************************
 * Copyright (c) 2013, 2014 Lectorius, Inc.
 * Authors:
 * Vijay Pandurangan (vijayp@mitro.co)
 * Evan Jones (ej@mitro.co)
 * Adam Hilss (ahilss@mitro.co)
 *
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *     
 *     You can contact the authors at inbound@mitro.co.
 *******************************************************************************/
package co.mitro.core.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.google.common.collect.Lists;

import co.mitro.core.exceptions.MitroServletException;
import co.mitro.core.server.Manager;
import co.mitro.core.server.data.DBAudit;
import co.mitro.core.server.data.DBIdentity;
import co.mitro.core.server.data.RPC;
import co.mitro.core.server.data.RPC.MitroRPC;


/**
 * Servlet implementation class BeginTransactionServlet
 */
@WebServlet("/api/BeginTransaction")

public class BeginTransactionServlet extends MitroServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected boolean isBeginTransactionOperation() { return true; }
  
  public static void beginTransaction(Manager manager, String operationName, DBIdentity requestor) throws SQLException {
    manager.setOperationName(operationName);
    manager.addAuditLog(DBAudit.ACTION.TRANSACTION_BEGIN, requestor, null, null, null, null); 
  }
	
  @Override
  protected MitroRPC processCommand(MitroRequestContext context) throws IOException, SQLException, MitroServletException {
    RPC.BeginTransactionRequest in = gson.fromJson(context.jsonRequest,
        RPC.BeginTransactionRequest.class);
    beginTransaction(context.manager, in.operationName, context.requestor); 
    
    return new RPC.BeginTransactionResponse();
    }
}